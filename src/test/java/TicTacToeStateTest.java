public class TicTacToeStateTest {
  public static void main(String[] args) {
  TicTacToeState mystate = new TicTacToeState(4,4);
  mystate.board[0][0] = 1;
  mystate.board[1][1] = 1;
  mystate.board[1][3] = 1;
  mystate.board[2][2] = 1;
  TicTacToeState newstate;
  newstate = mystate.clone();
  System.out.println(newstate.board[1][1]);
  }
}