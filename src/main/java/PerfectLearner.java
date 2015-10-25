import java.io.*;

public class PerfectLearner extends AbstractPlayer
{
  TicTacToeMove nextMove;
  double[][] qValues;
  double learningRate;
  double discountFactor;
  int previousIndex;
  int previousMove;

  int size = (int) Math.pow(3,9);
    
  public PerfectLearner(int playerNumber) {
    qValues = new double[size][9];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < 9; j++) {
        qValues[i][j] = 0.;
      }
    }
  }

  //  set learning parameteres
  public void setParameters(double learning_rate, double discount_factor) {
    learningRate = learning_rate;
    discountFactor = discount_factor;
  }

  //  returns an index of the state in the qValue array
  public int findIndex(TicTacToeState state) {
    int index = 0;
    for (int i = 0; i < state.sizeX; i++) {
      for (int j = 0; j < state.sizeY; j++) {
        index = 3 * index + state.board[i][j];
      }
    }
    return index;
  }

  public void updateQ(TicTacToeState state) {
    qValues[previousIndex][previousMove] = qValues[previousIndex][previousMove]
      + learningRate * ( reward(previousIndex) + discountFactor * 
        maxQDiff(state, previousIndex, previousMove));
  }

  private double reward(int previousIndex) {
    return -0.01;
  }

  public double maxQDiff(TicTacToeState state, int previousIndex,
    int previousMove) {
    double best = qValues[findIndex(state)][0] -
      qValues[previousIndex][previousMove];
    for (int i = 0; i < 9; i++) {
      if ((qValues[findIndex(state)][i] - 
        qValues[previousIndex][previousMove]) > best) {
        best = (qValues[findIndex(state)][i] - 
               qValues[previousIndex][previousMove]);
      }
    }
    return best;
  }

  public void saveBrain(){
      try {
	  File file = new File("brain.csv");
	  FileWriter fw = new FileWriter(file.getAbsoluteFile());
	  BufferedWriter bw = new BufferedWriter(fw);
	  for (int i = 0; i < size; i++){
	    bw.write(i);
	    for (int j = 0; j < 9; j++){
		    bw.write("," + qValues[i][j]);
	    }
	    bw.write("\n");
	  }
	  bw.close;
      } catch (IOException e){
	  e.printStackTrace();
      }
  }

  
    public void loadBrain(){}
    public void play_Learn(){}

  @Override
  public Move getMove(){
      return null;
  }

  @Override
  public void newMatch(){}

  @Override
  public void receiveState(GameState state){}
}
