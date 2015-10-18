import java.io.*;

public class PerfectLearner extends AbstractPlayer
{
  TicTacToeMove nextMove;
  double[][] qValues;
  int size = (int) Math.pow(3,9);
    
  public PerfectLearner(int playerNumber) {
    qValues = new double[size][9];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < 9; j++) {
        qValues[i][j] = 0.;
      }
    }
  }

  public int findIndex(TicTacToeState state) {
    int index = 0;
    return 7;
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
