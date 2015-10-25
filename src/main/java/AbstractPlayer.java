//  Abstract Player - abstraction with which Referee plays, to separate AI impl
public abstract class AbstractPlayer {
  public abstract void receiveState(GameState state);
  public abstract Move getMove();
  public abstract void newMatch();
  public void receiveResult(int result) {
    
  }
  protected int playerNumber;
  public int getPlayerNumber()
  {
      return playerNumber;
  }
}
