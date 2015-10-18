public class PerfectLearner extends AbstractPlayer
{
  TicTacToeMove nextMove;
  double[][] qValues;

  public PerfectLearner(int playerNumber) {
    int size = Math.pow(3,9);
    qValues = new double[size][9];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < 9; j++) {
        qValues[i][j] = 0.;
      }
    }
  }

  public int findIndex(TicTacToeState state) {
    int index = 0;
  }

  public void saveBrain();
  public void loadBrain();
  public void play_Learn();

  @Override
  public Move getMove();

  @Override
  public void newMatch();

  @Override
  public void receiveState(GameState state);


  int findIndex(TicTacToeState state) {

  }
