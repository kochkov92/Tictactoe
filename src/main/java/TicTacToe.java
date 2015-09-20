public class TicTacToe extends AbstractGame {
  //test
	private TicTacToeState board;
  private int moveNum;

  public TicTacToe(int sizeX, int sizeY) {
    this.board = new TicTacToeState(sizeX, sizeY);
    this.moveNum = 0;
  }
  @Override
  public boolean isOver() {
    //  Test if the game is over
    return false;
  }

  @Override
  public int whoseTurn() {
    return (moveNum % 2) + 1;  //  index of a player whos turn it is
  }

  @Override
  public GameState getState() {  //  returns a board
    return board.clone();
  }

  @Override
  public int computeWinner() {
    return whoseTurn();
  }

  @Override
  public void update(Move move) {
    
  }
}
