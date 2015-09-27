//  This is the main class for the launch
public class client {
  public static void main(String[] args) {
    //parser = new Parser(args);  //  a class to deal with input params
    //game = new TicTacToe(parser.size());  //  game class, contains dynamics
    //playerFactory = AIFactory(parser.numPlayers()); //  Factory of AI players
    //judge = new Referee(game, playerFactory.players()); //  ref
    //judge.tournament(parser.numGames());  //  sample start of N-game tournament
    //judge.getStatistics(); //  output the results of the tournament
    TicTacToe game = new TicTacToe(3,3);
    AbstractPlayer[] players = new AbstractPlayer[2];
    players[0] = new RealPlayer(1);
    players[1] = new RealPlayer(2);

    Referee judge = new Referee(game, players);
    judge.playMatch();
    System.out.println(game.getWinner());
  }
}
