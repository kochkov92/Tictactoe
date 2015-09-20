public class TicTacToeState extends GameState
{
	public int[][]	board;
	public int		sizeX, sizeY;

	public TicTacToeState(int xSize, int ySize)
	{
		board = new int[xSize][ySize];
		sizeX = xSize;
		sizeY = ySize;
		this.reset();
	}

	public void reset()
	{ // This method just sets the board to init state
		for (int i = 0; i < sizeX; i++)
		{
			for (int j = 0; j < sizeY; j++)
			{
				board[i][j] = 0;
			}
		}
	}

	@Override
	public TicTacToeState clone()
	{
		TicTacToeState outPutState = new TicTacToeState(sizeX, sizeY);
		for (int i = 0; i < sizeX; i++)
		{
			for (int j = 0; j < sizeY; j++)
			{
				outPutState.board[i][j] = board[i][j];
			}
		}
		return outPutState;
	}

	@Override
	public boolean equals(GameState state)
	{
		if (state instanceof TicTacToeState)
			return equals((TicTacToeState) (state));
		else
		{
			System.out.println("Not TicTacToeState");
			return false;
		}
	}

	private boolean equals(TicTacToeState state)
	{
		if (sizeX != state.sizeX || sizeY != state.sizeY)
		{
			System.out.println("Board Size not equal");
			return false;
		}
		else
		{
			for(int i = 0; i < sizeX; i++)
			{
				for(int j = 0; j < sizeY; j++)
				{
					if(board[i][j] != state.board[i][j])
					{
						System.out.println(i+","+j);
						return false;
					}
				}
			}
		}
		
		return true;

	}
	
	@Override
	public void print()
	{
	    for (int i = 0; i < this.sizeX; i++) {
	        for (int j =0; j < this.sizeY; j++) {
	          System.out.print(this.board[j][i] + " ");
	        }
	      System.out.print("\n");
	      }	
	}
}