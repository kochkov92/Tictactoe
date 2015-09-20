import java.util.Scanner;
public class RealPlayer extends AbstractPlayer {

  @Override
  public void receiveState(TicTacToeState state) {
	  state.print();
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