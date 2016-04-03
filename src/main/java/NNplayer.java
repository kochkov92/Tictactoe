import java.util.*;
// import Collections;

public class NNplayer extends AbstractPlayer {

  private int boardSize;  //length or width of board
  private boolean brainInitialized;
  public Vector<NeuralNetwork> brains;
  private Vector<Integer> scores;
  private int currentBrainIndex;
  private TicTacToeMove nextMove;
  private int numBrains;

  public NNplayer(int playerNumber, int board_size) {
    boardSize = board_size;
    this.playerNumber = playerNumber;
    brainInitialized = false;
    currentBrainIndex = 0;
    scores = new Vector<Integer>();
    brains = new Vector<NeuralNetwork>();
  }

  public void initializeBrain(int hiddenLayers, int hiddenNeurons) {
    numBrains = 36;
    for (int i = 0; i < numBrains; ++i) {
      NeuralNetwork brain = new NeuralNetwork(boardSize * boardSize, hiddenLayers, 
                            hiddenNeurons, boardSize * boardSize );
      brains.add(brain);
      scores.add(0);
    }
    brainInitialized = true;
  }

  private int findMove(Vector<Double> moveValues) {
    Double best = moveValues.get(0);
    int moveIndex = 0;
    for (int i = 1; i < moveValues.size(); ++i) {
      if (moveValues.get(i) > best) {
        best = moveValues.get(i);
        moveIndex = i;
      }
    }
    return moveIndex;
  }

  public TicTacToeMove getMove() {
    return nextMove;
  }


  private void receiveState(TicTacToeState state) {
    brains.get(currentBrainIndex).setUnknown();
    for(int i = 0; i < state.sizeX; i++) {
      for(int j = 0; j < state.sizeY; j++)
      {
        brains.get(currentBrainIndex).input.get(state.sizeY * i + j).setValue(state.board[i][j]);
      }
    }
    Vector<Double> moveValues = brains.get(currentBrainIndex).computeValue();
    int x, y;
    do {
      int index = findMove(moveValues);
      x = index / boardSize;
      y = index % boardSize;
      moveValues.set(index, -2000.);
    } while (state.board[x][y] != 0);
    nextMove = new TicTacToeMove(playerNumber,x,y);
  }

  @Override
  public void receiveState(GameState state)
  {
    receiveState((TicTacToeState)state);
  }

  @Override
  public void receiveResult(int result) {
    if (result == playerNumber) {
      scores.set(currentBrainIndex, scores.get(currentBrainIndex) + 2);
    }
    else if (result == -1) {
      scores.set(currentBrainIndex, scores.get(currentBrainIndex) + 1);
    }
    currentBrainIndex = (currentBrainIndex + 1) % numBrains;
  }

  public void evolve(Double amplitude, int brainsKept) {
    // Vector<Integer> bestBrainsIndices;
    Vector<NeuralNetwork> newBrains = new Vector<NeuralNetwork>();
    int copies = numBrains / brainsKept;
    printScores();
    for (int j = 0; j < brainsKept; ++j) {
      Integer bestScore = scores.get(0);
      Integer bestIndex = 0;
      for (int i = 1; i < numBrains; ++i) {
        if (scores.get(i) > bestScore) {
          bestIndex = i;
          bestScore = scores.get(i);
        }
      }
      // bestBrainsIndices.add(bestIndex);
      newBrains.add(brains.get(bestIndex));
      for (int k = 1; k < copies; ++k) {
        newBrains.add(brains.get(bestIndex));
        newBrains.lastElement().mutate(amplitude);
      }
      scores.set(bestIndex, -1 * scores.get(bestIndex));
    }
    brains = newBrains;
    clearScores();
  }

  private void clearScores() {
    for (int i = 0; i < numBrains; ++i) {
      scores.set(i, 0);
    }
  }

  public void printScores() {
    Vector<Integer> scoresCopy = new Vector<Integer>();
    scoresCopy = (Vector<Integer>)scores.clone();
    Collections.sort(scoresCopy);
    for (int i = 0; i < numBrains; ++i) {
      System.out.print(scoresCopy.get(i));
      System.out.print(" ");
    }
    System.out.println(" ");
  }

  @Override
  public void newMatch()
  {
    // TODO Auto-generated method stub

  }
}