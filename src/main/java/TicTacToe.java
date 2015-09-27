public class TicTacToe extends AbstractGame
{
	// test
	private TicTacToeState	state;
	private int				moveNum;
	private int				winner;			// who is a current winner
	private int				winningLength;	// how many pieces grant victory

	public TicTacToe(int sizeX, int sizeY)
	{
		this.state = new TicTacToeState(sizeX, sizeY);
		this.moveNum = 0;
		this.winner = 0; // zero for no winner, player id otherwise
    this.winningLength = 3;
	}

	@Override
	public boolean isOver(Move move)
	{
		if (move instanceof TicTacToeMove)
		{
			return isOver((TicTacToeMove) move);
		} else
		{
			return false;
		}

	}

	/**
	 * Checks whether win condition starting at x, y to x+n, y+n
	 * 
	 * @param x
	 *            initial x coordinate for a check
	 * @param y
	 *            initial y coordinate for a check
	 * @return true if there is a winning condition
	 */
	private boolean checkDiagonal(int x, int y)
	{
		if (x < 0 || y < 0 || x >= this.state.sizeX - winningLength + 1
				|| y >= this.state.sizeY - winningLength + 1)
		{
			return false;
		}
		else
		{
			for (int i = 0; i < winningLength; i++)
			{
				if (this.state.board[x + i][y + i] != this.state.board[x][y])
				{
					return false;
				}
			}
      
			return true;
		}
	}
	
	private boolean checkAntiDiagonal(int x, int y)
	{
		if (x < winningLength -1 || y < 0 || x >= this.state.sizeX
				|| y >= this.state.sizeY - winningLength + 1)
		{
			return false;
		}
		else
		{
			for (int i = 0; i < winningLength; i++)
			{
				if (this.state.board[x - i][y + i] != this.state.board[x][y])
				{
					return false;
				}
			}
			return true;
		}
	}
	
	private boolean checkHorizontal(int x, int y)
	{
		if (x < 0 || y < 0 || x >= this.state.sizeX - winningLength + 1
				|| y >= this.state.sizeY)
		{
			return false;
		}
		else
		{
			for (int i = 0; i < winningLength; i++)
			{
				if (this.state.board[x + i][y] != this.state.board[x][y])
				{
					return false;
				}
			}

			return true;
		}
	}
	
	private boolean checkVertical(int x, int y)
	{
		if (x < 0 || y < 0 || x >= this.state.sizeX
				|| y >= this.state.sizeY - winningLength + 1)
		{
			return false;
		}
		else
		{
			for (int i = 0; i < winningLength; i++)
			{
				if (this.state.board[x][y + i] != this.state.board[x][y])
				{
					return false;
				}
			}
      
			return true;
		}
	}
	

	private boolean isOver(TicTacToeMove move)
	{
		int x = move.getX();
		int y = move.getY();
		
		return (this.checkAntiDiagonal(x, y) || this.checkDiagonal(x, y)
				|| this.checkHorizontal(x, y) || this.checkVertical(x, y));
	}

	@Override
	public int getTurn()
	{
		return (moveNum % 2) + 1; // index of a player whose turn it is
	}

	@Override
	public GameState getState()
	{ // returns a board
		return state.clone();
	}

	@Override
	public int getWinner()
	{ //
		return winner;
	}

	@Override
	public int update(Move move)
	{
		if (move instanceof TicTacToeMove)
		{
			return update((TicTacToeMove) move);
		}
		return 2;
	}

	private int update(TicTacToeMove move)
	{
		if (this.state.board[move.getX()][move.getY()] == 0 &&
				this.getTurn() == move.getPlayer())
		{
			this.state.board[move.getX()][move.getY()] = move.getPlayer();
			if (this.isOver(move))
			{
				this.winner = this.getTurn();
			}
			this.moveNum++;
			return 0;
		} else
		{
			return 1;
		}
	}

}
