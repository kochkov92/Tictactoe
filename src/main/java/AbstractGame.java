//  This is an abstract class for a game with common methods
public abstract class AbstractGame {
  private GameState state;

  public abstract boolean isOver() {};  
  public abstract int whoseTurn() {};  //  returns an index of a player
  public abstract void update(class Move) {};  //  updates the state
  public abstract GameState getState() {};  //  returns current state
  public abstract int computeWinner() {};  //  returns index of the winner
}