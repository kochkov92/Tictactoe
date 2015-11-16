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

    players[1] = new RandomPlayer(2);
    players[0] = new PerfectLearner(1); //RealPlayer(2); //
    ((PerfectLearner)players[0]).loadBrain("brain");
    //((PerfectLearner)players[0]).clearBrain("brain");

    
    Referee judge = new Referee(game, players);
    for (int i = 0; i < 50; i++) {
      System.out.print(i + ": ");
      judge.playTournament(10000);
    }
    ((PerfectLearner)players[0]).setExploring(false);

    // for (int i = 200; i < 400; i++) {
    //   System.out.print(i + ": ");
    //   judge.playTournament(100000);
    // }
    for (int j = 0; j < 1; j++) {
      judge.gameWithOutput();
    }
    // System.out.println(game.getWinner());

    ((PerfectLearner)players[0]).saveBrain("brain");
  }
}
