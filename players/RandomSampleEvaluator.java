package players;

import gamer.Game;
import gamer.GameState;
import gamer.Move;

class RandomSampleEvaluator<G extends Game> implements Evaluator<G> {
  public int evaluate(GameState<G> origState) {
    GameState<G> state = origState.clone();
    while (!state.isTerminal()) {
      state.play(state.getRandomMove());
    }

    return state.getResult().asInt();
  }

  @Override
  public RandomSampleEvaluator<G> clone() {
    return new RandomSampleEvaluator<G>();
  }
}