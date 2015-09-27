import java.util.Scanner;
public class RealPlayer extends AbstractPlayer {

  @Override
  public void receiveState(GameState state) {
	  state.print();
  }

  @Override
  public void newMatch() {
    System.out.println("new match!");
  }

  @Override
  public Move getMove() {
    Scanner input = new Scanner(System.in);
    int player, x, y;
    if (input.hasNext()) {
      player = input.nextInt();
      input.nextLine();
      x = input.nextInt();
      input.nextLine();
      y = input.nextInt();
      input.nextLine();
    }
    else {
      player = 0;
      x = 2;
      y = 1;
    }
    return new TicTacToeMove(player, x, y);
  }
}