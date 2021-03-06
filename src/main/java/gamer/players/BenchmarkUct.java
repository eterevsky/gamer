package gamer.players;

import gamer.benchmark.Benchmark;
import gamer.chess.Chess;
import gamer.chess.ChessMove;
import gamer.chess.ChessState;
import gamer.gomoku.Gomoku;
import gamer.gomoku.GomokuMove;
import gamer.gomoku.GomokuState;

public class BenchmarkUct {
  // @Benchmark
  public static ChessMove uctChess20kSamplesMulti(int reps) {
    int cores = Runtime.getRuntime().availableProcessors();

    MonteCarloUct<ChessState, ChessMove> player = new MonteCarloUct<>(Chess.getInstance());
    player.setMaxSamples(20000);
    player.setTimeout(-1);
    player.setSamplesBatch(1);
    player.setFindExact(true);
    player.setMaxWorkers(cores);
    player.setSelector(Chess.getInstance().getRandomMoveSelector());

    ChessMove move = null;
    for (int i = 0; i < reps; i++) {
      ChessState s = Chess.getInstance().newGame();
      move = player.selectMove(s);
    }

    return move;
  }

  // @Benchmark
  public static ChessMove uctChess20kSamplesSingle(int reps) {
    MonteCarloUct<ChessState, ChessMove> player = new MonteCarloUct<>(Chess.getInstance());
    player.setMaxSamples(20000);
    player.setTimeout(-1);
    player.setSamplesBatch(1);
    player.setFindExact(true);
    player.setSelector(Chess.getInstance().getRandomMoveSelector());

    ChessMove move = null;
    for (int i = 0; i < reps; i++) {
      ChessState s = Chess.getInstance().newGame();
      move = player.selectMove(s);
    }

    return move;
  }

  @Benchmark
  public static GomokuMove uctGomoku100kSamplesMulti(int reps) {
    int cores = Runtime.getRuntime().availableProcessors();

    MonteCarloUct<GomokuState, GomokuMove> player = new MonteCarloUct<>(Gomoku.getInstance());
    player.setMaxSamples(100000);
    player.setTimeout(-1);
    player.setSamplesBatch(1);
    player.setFindExact(true);
    player.setMaxWorkers(cores);
    player.setSelector(Gomoku.getInstance().getRandomMoveSelector());

    GomokuMove move = null;
    for (int i = 0; i < reps; i++) {
      GomokuState s = Gomoku.getInstance().newGame();
      move = player.selectMove(s);
    }

    return move;
  }

  @Benchmark
  public static GomokuMove uctGomoku100kSamplesSingle(int reps) {
    MonteCarloUct<GomokuState, GomokuMove> player = new MonteCarloUct<>(Gomoku.getInstance());
    player.setMaxSamples(100000);
    player.setTimeout(-1);
    player.setSamplesBatch(1);
    player.setFindExact(true);
    player.setSelector(Gomoku.getInstance().getRandomMoveSelector());

    GomokuMove move = null;
    for (int i = 0; i < reps; i++) {
      GomokuState s = Gomoku.getInstance().newGame();
      move = player.selectMove(s);
    }

    return move;
  }
}
