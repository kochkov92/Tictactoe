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
    AbstractPlayer[] players = new AbstractPlayer[3];
    
    // players[1] = new NNplayer(1,3); //RealPlayer(2);
    // players[1] = new PerfectLearner(2);
    players[1] = new RandomPlayer(2); //RandomPlayer(2);
    players[0] = new NNplayer(1,3); //RealPlayer(2);
    ((NNplayer)players[0]).initializeBrain(2,8);
    // ((NNplayer)players[1]).initializeBrain(1,7);
    //((PerfectLearner)players[0]).loadBrain("brain");
    // players[0] = new RandomPlayer(1);
    //((PerfectLearner)players[0]).loadBrain("brain");
    //((PerfectLearner)players[0]).clearBrain("brain");
    //((PerfectLearner)players[0]).setExploring(false);
    
    Referee judge = new Referee(game, players);
    for (int i = 0; i < 300; i++) {
      System.out.print(i + ": ");
      judge.playTournament(24000 * 3);
      ((NNplayer)players[0]).evolve(0.00, 2);
      // ((NNplayer)players[1]).evolve(5., 2);
    }    // for (int i = 0; i < 50; i++) {
    //   System.out.print(i + ": ");
    //   judge.playTournament(10000);
    // }
    // ((PerfectLearner)players[0]).setExploring(false);
    ((NNplayer)players[0]).brains.get(0).print();
    judge.playMatch(true);
    players[1] = new RealPlayer(2);
    judge.playMatch(true);

    // for (int i = 0; i < 50; i++) {
    //   System.out.print(i + ": ");
    //   judge.playTournament(10000);
    // }

    // for (int i = 200; i < 400; i++) {
    //   System.out.print(i + ": ");
    //   judge.playTournament(100000);
    // }
    // for (int j = 0; j < 1; j++) {
    //   judge.gameWithOutput();
    // }
    // System.out.println(game.getWinner());

    // ((PerfectLearner)players[0]).saveBrain("brain");
  }
}
