package gamer.players;

import gamer.def.Game;
import gamer.def.GameState;
import gamer.def.Helper;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Sampler<G extends Game> implements Runnable {
  private final Node<G> root;
  private final long finishTime;
  private final long maxSamples;
  private final int samplesBatch;
  private final Random random;
  private Helper<G> helper = null;

  Sampler(
      Node<G> root, long finishTime, long maxSamples, int samplesBatch,
      Random random) {
    this.root = root;
    this.finishTime = finishTime;
    this.maxSamples = maxSamples;
    this.samplesBatch = samplesBatch;
    this.random = random;
  }

  void setHelper(Helper<G> helper) {
    this.helper = helper;
  }

  public void run() {
    Random rnd = random == null ? ThreadLocalRandom.current() : random;

    while (!root.knowExactValue() &&
           (maxSamples <= 0 || root.getSamples() < maxSamples) &&
           (finishTime <= 0 || System.currentTimeMillis() < finishTime)) {
      Node<G> node = null;
      Node<G> child = root;
      do {
        node = child;
        child = node.selectChildOrAddPending(samplesBatch);
      } while (child != null && child != Node.KNOW_EXACT_VALUE);

      if (child == Node.KNOW_EXACT_VALUE)
        continue;

      double value = 0;
      for (int i = 0; i < samplesBatch; i++) {
        GameState<G> state = node.getState();
        Helper.Result hResult;
        do {
          state = state.play(state.getRandomMove(rnd));
          hResult = helper != null ? helper.evaluate(state) : null;
        } while (!state.status().isTerminal() && hResult == null);

        if (state.status().isTerminal()) {
          value += state.status().value();
        } else {
          value += hResult.value();
        }
      }
      node.addSamples(samplesBatch, value / samplesBatch);
    }
  }
}