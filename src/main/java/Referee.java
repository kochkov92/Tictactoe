import java.util.LinkedList;

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

  public Referee(AbstractGame mygame, AbstractPlayer[] players) {
    winnerOne = 0;
  	this.mygame = mygame;
  	this.players = players;
  
  };  //  constructor
  public void playMatch()
    {
	AbstractPlayer currentPlayer;
	while(mygame.getWinner() == 0) {
  		currentPlayer = players[mygame.getTurn() - 1];
  		currentPlayer.receiveState(mygame.getState());
  		mygame.update(currentPlayer.getMove());
	  }
    if (mygame.getWinner()==1) {
      winnerOne++;
    }
    players[0].receiveResult(mygame.getWinner());
    players[1].receiveResult(mygame.getWinner());

	System.out.println("winner is " + mygame.getWinner());
    }  //  plays one round
       //  
  public void playTournament(int numGames)
    {
	for(int i = 0; i < numGames; i++);
	{
	    playMatch();
	}
    };  //  plays numGames games
  public void getStatistics() {};  //  outputs statistics of Tourn. to SO.
}
