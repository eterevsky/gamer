package gamer.players;

import gamer.treegame.TreeGameInstances;
import gamer.treegame.TreeGameMove;
import gamer.treegame.TreeGameState;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class TestMonteCarloUct {

  @Test(timeout=100)
  public void play0() {
    TreeGameState state = TreeGameInstances.GAME0.newGame();
    MonteCarloUct<TreeGameState, TreeGameMove> player =
        new MonteCarloUct<>(TreeGameInstances.GAME0);
    player.setTimeout(-1);
    player.setMaxSamples(50L);
    player.setSamplesBatch(1);
    player.setSelector(TreeGameInstances.GAME0.getRandomMoveSelector());

    TreeGameMove move = player.selectMove(state);
    assertEquals(1, move.getNodeId());
  }

  @Test(timeout=100)
  public void play1() {
    TreeGameState state = TreeGameInstances.GAME1.newGame();
    MonteCarloUct<TreeGameState, TreeGameMove> player = new MonteCarloUct<>(TreeGameInstances.GAME1);
    player.setTimeout(-1);
    player.setMaxSamples(50L);
    player.setSamplesBatch(1);
    player.setSelector(TreeGameInstances.GAME1.getRandomMoveSelector());

    TreeGameMove move = player.selectMove(state);
    assertEquals(1, move.getNodeId());
    state.play(move);

    move = player.selectMove(state);
    assertEquals(3, move.getNodeId());
  }

  @Test(timeout=500)
  public void play2() {
    TreeGameState state = TreeGameInstances.GAME2.newGame();
    MonteCarloUct<TreeGameState, TreeGameMove> player = new MonteCarloUct<>(TreeGameInstances.GAME2);
    player.setTimeout(-1);
    player.setMaxSamples(50L);
    player.setSamplesBatch(1);
    player.setSelector(TreeGameInstances.GAME2.getRandomMoveSelector());

    TreeGameMove move = player.selectMove(state);
    assertEquals(1, move.getNodeId());
    state.play(move);

    state.play(player.selectMove(state));
    state.play(player.selectMove(state));

    assertTrue(state.isTerminal());
    assertEquals(1, state.getPayoff(0));
  }

  @Test(timeout=100)
  public void play2player2() {
    TreeGameState state = TreeGameInstances.GAME2.newGame();
    MonteCarloUct<TreeGameState, TreeGameMove> player = new MonteCarloUct<>(TreeGameInstances.GAME2);
    player.setTimeout(-1);
    player.setMaxSamples(50L);
    player.setSamplesBatch(1);
    player.setSelector(TreeGameInstances.GAME2.getRandomMoveSelector());

    state.play(state.getMoveToNode(2));
    TreeGameMove move = player.selectMove(state);
    assertEquals(5, move.getNodeId());
  }

  @Test(timeout=100)
  public void play3() {
    TreeGameState state = TreeGameInstances.GAME3.newGame();
    MonteCarloUct<TreeGameState, TreeGameMove> player = new MonteCarloUct<>(TreeGameInstances.GAME3);
    player.setTimeout(-1);
    player.setMaxSamples(50L);
    player.setSamplesBatch(1);
    player.setSelector(TreeGameInstances.GAME3.getRandomMoveSelector());

    TreeGameMove move = player.selectMove(state);
    assertEquals(2, move.getNodeId());
    state.play(move);

    move = player.selectMove(state);
    assertEquals(5, move.getNodeId());
  }

  @Test(timeout=500)
  public void play4() {
    TreeGameState state = TreeGameInstances.GAME4.newGame();
    MonteCarloUct<TreeGameState, TreeGameMove> player = new MonteCarloUct<>(TreeGameInstances.GAME4);
    player.setTimeout(-1);
    player.setMaxSamples(1000L);
    player.setSamplesBatch(1);
    player.setSelector(TreeGameInstances.GAME4.getRandomMoveSelector());

    TreeGameMove move = player.selectMove(state);
    assertEquals(2, move.getNodeId());
    state.play(move);
    state.play(state.getMoveToNode(4));

    move = player.selectMove(state);
    assertEquals(7, move.getNodeId());
  }
}
