//  This is an abstract class for a game with common methods
public abstract class AbstractGame {
  private GameState state;
  public abstract void reset();
  public abstract boolean isOver(Move move);  
  public abstract int getTurn();  //  returns an index of a player
  public abstract int update(Move move);  //  updates the state
  public abstract GameState getState();  //  returns current state
  public abstract int getWinner();  //  returns index of the winner
  public abstract void print();                                  
  
}
