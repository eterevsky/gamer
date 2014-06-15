import gamer.GameState;
import gamer.Move;
import gamer.Player;
import gomoku.Gomoku;
import players.MonteCarloUcb;
import players.NaiveMonteCarlo;
import players.RandomPlayer;

class App {
  public static void main(String[] args) throws Exception {
    GameState<Gomoku> game = Gomoku.newGame();
    Player player1 = new NaiveMonteCarlo().setTimeout(2);
    Player player2 = new MonteCarloUcb().setTimeout(2);

    System.out.println(game);

    while (!game.isTerminal()) {
      Move<Gomoku> move;
      if (game.getPlayer()) {
        move = player1.selectMove(game);
      } else {
        move = player2.selectMove(game);
      }
      game.play(move);
      System.out.println(game);
    }
  }
}
