public abstract class GameState {
  //  should we add something here??
  public abstract GameState clone();

public abstract boolean equals(GameState state);
public abstract void print();
}
