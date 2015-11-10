//  This is the main class for the launch
public class client {
  public static void main(String[] args) throws java.io.FileNotFoundException {
    //parser = new Parser(args);  //  a class to deal with input params
    //game = new TicTacToe(parser.size());  //  game class, contains dynamics
    //playerFactory = AIFactory(parser.numPlayers()); //  Factory of AI players
    //judge = new Referee(game, playerFactory.players()); //  ref
    //judge.tournament(parser.numGames());  //  sample start of N-game tournament
    //judge.getStatistics(); //  output the results of the tournament
    TicTacToe game = new TicTacToe(3,3);
    AbstractPlayer[] players = new AbstractPlayer[2];
    players[0] = new RandomPlayer(1);
    players[1] = new PerfectLearner(2); //RealPlayer(2); //
    ((PerfectLearner)players[1]).loadBrain();
    
    Referee judge = new Referee(game, players);
    judge.playTournament(1000000);
    System.out.println(game.getWinner());

    ((PerfectLearner)players[1]).saveBrain();
  }
}
