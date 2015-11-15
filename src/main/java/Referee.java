import java.util.*;

//  Referee interacts between the given game and players and does statistics
public class Referee {
  GameRecorder stats;
  private AbstractGame mygame;
  private AbstractPlayer[] players;
  private LinkedList<Move> moves;
  private void resetGame() {};  //  reset for a new round
  private void recordTheMove(TicTacToeMove move){};
  private int winnerOne;
  private void collectStats() {};  //  collect info who won, who lost
    private int player1WinCount = 0;
    private int player2WinCount = 0;

  public Referee(AbstractGame mygame, AbstractPlayer[] players) {
    winnerOne = 0;
  	this.mygame = mygame;
  	this.players = players;
  
  };  //  constructor
  public void playMatch(boolean shouldPrint)
    {
	AbstractPlayer currentPlayer;
	do {
    if (shouldPrint) {
      mygame.print();
    }
  		currentPlayer = players[mygame.getTurn() - 1];
		currentPlayer.receiveState(mygame.getState());
  		mygame.update(currentPlayer.getMove());
	} while(!mygame.isOver(currentPlayer.getMove()));
    if (mygame.getWinner()==1) {
      winnerOne++;
    }
    players[0].receiveResult(mygame.getWinner());
    players[1].receiveResult(mygame.getWinner());
    // System.out.println("winner is " + mygame.getWinner());
    if (mygame.getWinner() == 1)
	{
	    player1WinCount++;
	}
    else if (mygame.getWinner() == 2)
	{
    if (shouldPrint == true) {
      // mygame.print();
    }
	    player2WinCount++;
	}
    
    mygame.reset();
    }  //  plays one round
       //  
  public void gameWithOutput() {
    playMatch(true);
  }
  public void playTournament(int numGames)
  {
      player2WinCount = 0;
      player1WinCount = 0;

      for(int i = 0; i < numGames; i++)
      {
          // System.out.println("Match number: " + i);
          playMatch(false);
      }
      double player1WinRate = 1.0*player1WinCount/numGames;
      double player2WinRate = 1.0*player2WinCount/numGames;
      double tieRate = 1.0*(numGames - player1WinCount - player2WinCount)/numGames;
      System.out.println(player1WinRate + " " + player2WinRate);


      // System.out.println("Player 1 won " + player1WinRate + " of " + numGames);
      // System.out.println("Player 2 won " + player2WinRate + " of " + numGames);
      // System.out.println("Tie rate: " + tieRate);
	
    };  //  plays numGames games
  public void getStatistics() {};  //  outputs statistics of Tourn. to SO.
}
