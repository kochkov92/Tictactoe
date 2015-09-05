//  Referee interacts between the given game and players and does statistics
public class Referee {
  GameRecorder stats;

  private resetGame() {};  //  reset for a new round
  private recordTheMove() {};  //  record the move tha player did
  private collectStats() {};  //  collect info who won, who lost

  public Referee(AbstractGame mygame, AbstractPlayer[] players) {};  //  constr
  public void playRound() {};  //  plays one round
  public void playTournament(int numGames) {};  //  plays numGames games
  public void getStatistics() {};  //  outputs statistics of Tourn. to SO.

}