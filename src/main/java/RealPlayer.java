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
<<<<<<< HEAD
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
=======
    System.out.print("Enter x coordinate:");
    int x = input.nextInt();
    System.out.print("Enter y coordinate:");
    int y = input.nextInt();
    return new TicTacToeMove(playerNumber, x, y);
>>>>>>> 0fbe046188b34a59578a4373772452a3d6910cd9
  }
}
