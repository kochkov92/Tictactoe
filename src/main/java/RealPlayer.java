import java.util.Scanner;
public class RealPlayer extends AbstractPlayer {

  public RealPlayer(int playerNumber)
  {
      this.playerNumber = playerNumber;
  }
    
  @Override
  public void receiveState(GameState state) {
	  state.print();
	  System.out.println("player " + playerNumber + "'s turn");
  }

  @Override
  public void newMatch() {
    System.out.println("new match!");
  }

  @Override
  public Move getMove() {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter x coordinate:");
    int x = input.nextInt();
    System.out.println("Enter y coordinate:");
    int y = input.nextInt();
    return new TicTacToeMove(playerNumber, x, y);
  }
}
