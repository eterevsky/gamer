package gamer;

import gamer.gomoku.TestGomokuMove;
import gamer.gomoku.TestGomokuState;
import gamer.players.TestRandomPlayer;
import gamer.players.TestMonteCarloUcb;
import gamer.treegame.TestTreeGame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestGomokuMove.class,
  TestGomokuState.class,

  TestMonteCarloUcb.class,
  TestRandomPlayer.class,

  TestTreeGame.class
})

public class TestMain {
}
