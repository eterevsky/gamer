package gamer.gomoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import gamer.def.TerminalPositionException;
import org.junit.Test;

import gamer.def.GameException;

import java.util.Random;

public class TestGomokuState {
  private GomokuState playGame(String gameStr, int size) {
    GomokuState state = Gomoku.getInstance(size).newGame();
    boolean player = true;

    for (String moveStr : gameStr.split(" ")) {
      assertEquals(player, state.getPlayerBool());
      assertFalse(state.isTerminal());
      state.play(state.parseMove(moveStr));
      player = !player;
    }

    return state;
  }

  private void testPlayGame(String gameStr, int expectedPayoff) {
    GomokuState state = playGame(gameStr, 19);
    assertTrue(state.isTerminal());
    assertEquals(expectedPayoff, state.getPayoff(0));
  }

  @Test(timeout=50)
  public void playVertical() {
    testPlayGame("c3 d3 c4 b4 c5 c2 c6 e6 c7", 1);
  }

  @Test(timeout=50)
  public void playHorizontal() {
    testPlayGame("c3 d3 c4 b4 c5 c2 c6 e6 c7", 1);
  }

  @Test(timeout=50)
  public void playDiagonal1() {
    testPlayGame("e5 f4 f5 g5 e3 e6 f6 h6 g7 h8 h7 k8 d4 j7", -1);
  }

  @Test(timeout=50)
  public void playDiagonal2() {
    testPlayGame(
        "e6 e7 f7 f6 d8 e8 e9 d7 f8 g5 g7 h6 d10 c7 c11", 1);
  }

  @Test(timeout=50)
  public void playBorders() {
    testPlayGame(
        "q10 q11 r10 r11 s10 s11 t10 t11 a11 a10 b11 b10 c11 c10 d11 d10 " +
        "c12 c9 b13 b8 a14 a7 t15 t6 s16 s5 r17 r4 q18 q3 p19",
        1);
  }

  @Test(timeout=50)
  public void playHorizontalOverlap() {
    GomokuState state = playGame("c3 a2 e4 b2 b4 c2 a4 t1 m11 s1", 19);
    assertFalse(state.isTerminal());
  }

  @Test(expected = TerminalPositionException.class, timeout=50)
  public void playNoMoveAfterEnd() {
    GomokuState state = playGame("c4 g6 e4 f7 b4 e8 a4 d9 m11 c10", 19);
    assertEquals(-1, state.getPayoff(0));
    state.play(state.parseMove("b2"));
  }

  @Test(expected = GameException.class, timeout=50)
  public void playTwoMovesBySamePlace() {
    GomokuState state = Gomoku.getInstance().newGame();
    state.play(state.parseMove("c4"));
    state.play(state.parseMove("c4"));
  }

  @Test(timeout=100)
  public void playDraw() {
		Gomoku gomoku = Gomoku.getInstance();
    GomokuState state = gomoku.newGame();
    for (int i = 0; i < gomoku.getSize(); i++) {
      int row;
      switch (i % 4) {
        case 1: row = i + 1; break;
        case 2: row = i - 1; break;
        default: row = i;
      }

      for (int j = 0; j < gomoku.getSize(); j++) {
        state.play(GomokuMove.of(row, j, gomoku.getSize()));
      }
    }

    assertTrue(state.isTerminal());
    assertEquals(0, state.getPayoff(0));
  }

  @Test(timeout=100)
  public void playRandomMoves() {
		Gomoku gomoku = Gomoku.getInstance();
    GomokuState state = gomoku.newGame();
    Random random = new Random(1234567890L);

    assertTrue(state.getPlayerBool());
    int moves = 0;

    while (!state.isTerminal()) {
      state.play(state.getRandomMove(random));
      moves++;
    }

    assertTrue(moves >= 9);
    assertTrue(moves <= gomoku.getSize() * gomoku.getSize());
  }

  @Test(timeout=50)
  public void playRandomOnSmallBoard() {
    Gomoku gomoku = Gomoku.getInstance(4);

    Random random = new Random(1234567890L);
    for (int igame = 0; igame < 10; igame++) {
      GomokuState state = gomoku.newGame();
      int moves = 0;
      while (!state.isTerminal()) {
        state.play(state.getRandomMove(random));
        moves++;
      }

      assertEquals(16, moves);
      assertTrue(state.isTerminal());
      assertEquals(0, state.getPayoff(0));
    }
  }
}
