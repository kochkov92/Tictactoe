import java.util.Scanner;
public class RealPlayer extends AbstractPlayer {

  @Override
  public void receiveState(TicTacToeState state) {
    for (int i = 0; i < state.sizeX; i++) {
      for (int j =0; j < state.sizeY; j++) {
        System.out.print(state.board[i][j] + " ");
      }
    System.out.print("\n");
    }
  }

  @Override
  public void newMatch() {
    System.out.println("new match!");
  }

  @Override
  public Move getMove() {
    Scanner input = new Scanner(System.in);
    int player = input.nextInt();
    int x = input.nextInt();
    int y = input.nextInt();
    return new TicTacToeMove(player, x, y);
  }
}