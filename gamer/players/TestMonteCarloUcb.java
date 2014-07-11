package gamer.players;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gamer.def.GameResult;
import gamer.treegame.TreeGame;
import gamer.treegame.TreeGameMove;
import gamer.treegame.TreeGameInstances;
import gamer.treegame.TreeGameState;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class TestMonteCarloUcb {

  @Test
  public void play0() {
    TreeGame game = TreeGameInstances.GAME0;

    TreeGameState state = game.newGame();
    MonteCarloUcb<TreeGame> player = new MonteCarloUcb<>();
    player.setTimeout(-1).setSamplesLimit(50L).setSamplesBatch(1);

    TreeGameMove move = (TreeGameMove) player.selectMove(state);
    assertEquals(1, move.getNodeId());
  }

  @Test
  public void play1() {
    TreeGame game = TreeGameInstances.GAME1;

    TreeGameState state = game.newGame();
    MonteCarloUcb<TreeGame> player = new MonteCarloUcb<>();
    player.setTimeout(-1).setSamplesLimit(50L).setSamplesBatch(1);

    TreeGameMove move = (TreeGameMove) player.selectMove(state);
    assertEquals(1, move.getNodeId());
    state.play(move);

    move = (TreeGameMove) player.selectMove(state);
    assertEquals(3, move.getNodeId());
  }

  @Test
  public void play3() {
    TreeGame game = TreeGameInstances.GAME3;

    TreeGameState state = game.newGame();
    MonteCarloUcb<TreeGame> player = new MonteCarloUcb<>();
    player.setTimeout(-1).setSamplesLimit(50L).setSamplesBatch(1);

    TreeGameMove move = (TreeGameMove) player.selectMove(state);
    assertEquals(2, move.getNodeId());
    state.play(move);

    move = (TreeGameMove) player.selectMove(state);
    assertEquals(5, move.getNodeId());
  }
}