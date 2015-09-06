public class TicTacToeState extends GameState {
  public int[][] board;
  public xSize, ySize;

  public TicTacToeState(int sizeX, int sizeY) {
    board = new int[sizeX][sizeY];
    xSize = sizeX;
    ySzie = sizeY;
  }

  @Override
  public GameState clone() {
    TicTacTieState outPutState = new TicTacToeState(xSize, ySize);
    for (int i = 0; i < xSize; i++) {
      for (int j = 0; j < ySize; j++) {
        outPutState.board[i][j] = board[i][j];
      }
    }
    return outPutState;
  }

}