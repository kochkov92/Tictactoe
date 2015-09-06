public class TicTacToeState extends GameState {
  public int[][] board;
  public int sizeX, sizeY;

  public TicTacToeState(int xSize, int ySize) {
    board = new int[xSize][ySize];
    sizeX = xSize;
    sizeY = ySize;
  }

  @Override
  public TicTacToeState clone() {
    TicTacToeState outPutState = new TicTacToeState(sizeX, sizeY);
    for (int i = 0; i < sizeX; i++) {
      for (int j = 0; j < sizeY; j++) {
        outPutState.board[i][j] = board[i][j];
      }
    }
    return outPutState;
  }

}