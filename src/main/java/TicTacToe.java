public class TicTacToe extends AbstractGame {
  //test
	private TicTacToeState board;

  public TicTacToe(int sizeX, int sizeY) {
    this.board = new TicTacToeState(sizeX, sizeY);
  }
  @Override
  public boolean isOver() {
    //  Test if the game is over
    return false;
  }

  @Override
  public int whoseTurn() {
    return 1;//  whose turn is it??
  }

  @Override
  public GameState getState() {
    return board;
  }

  @Override
  public int computeWinner() {
    return 1;
  }

  @Override
  public void update(Move move) {
    
  }
}
