import java.util.*;

public class RandomPlayer extends AbstractPlayer
{

	TicTacToeMove nextMove;
	
	public RandomPlayer(int playerNumber)
	{
		this.playerNumber = playerNumber;
	}
	
	@Override
	public void receiveState(GameState state)
	{
		if(state instanceof TicTacToeState)
		{
			TicTacToeState ticState = (TicTacToeState)state;
			
			LinkedList<TicTacToeMove> possibleMoves = new LinkedList<TicTacToeMove>();
				
			for(int i = 0; i < ticState.sizeX;i++)
			{
				for(int j = 0; j < ticState.sizeY;j++)
				{
					if(ticState.board[i][j] == 0)
					{
						TicTacToeMove posMove = new TicTacToeMove(this.playerNumber,i,j);
						possibleMoves.add(posMove);
					}
				}
				
			}
			
			
			Random rand = new Random();
			nextMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
				
		}

	}

	@Override
	public Move getMove()
	{
		return nextMove;
	}

	@Override
	public void newMatch()
	{
		// TODO Auto-generated method stub

	}

}
