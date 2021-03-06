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
        public void reset(){
	    state.reset();
	    this.moveNum = 0;
	    this.winner = 0;
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
		if (x < 0 || y < 0 || x >= this.state.sizeX
        || y >= this.state.sizeY)
    {
      return false;
    }
    else
    {
        int count = 1;
        int i = 1;
        while(x + i < this.state.sizeX && y + i < this.state.sizeY &&
          this.state.board[x + i][y + i] == this.state.board[x][y] ) {
          count++;
          i++;
      }
        i = 1;
        while(x - i >= 0 && y - i >= 0 && 
          this.state.board[x - i][y - i] == this.state.board[x][y])
      {
          count++;
          i++;
      }
        if(count >= winningLength)
      {
          // System.out.println("Diagonal win");
          return true;
      }
        else
      {
          return false;
      }
    }
	}
	
	private boolean checkAntiDiagonal(int x, int y)
	{
		if (x < 0 || y < 0 || x >= this.state.sizeX
        || y >= this.state.sizeY)
    {
      return false;
    }
    else
    {
        int count = 1;
        int i = 1;
        while(x + i < this.state.sizeX && y - i >= 0 && 
          this.state.board[x + i][y - i] == this.state.board[x][y]) {
          count++;
          i++;
      }
        i = 1;
        while(x - i >= 0 && y + i < this.state.sizeY && 
          this.state.board[x - i][y + i] == this.state.board[x][y])
      {
          count++;
          i++;
      }
        if(count >= winningLength)
      {
          //System.out.println("AntiDiagonal win");
          return true;
      }
        else
      {
          return false;
      }
    }
	}
	
	private boolean checkHorizontal(int x, int y) {
    if (x < 0 || y < 0 || x >= this.state.sizeX
        || y >= this.state.sizeY)
    {
      return false;
    }
    else
    {
        int count = 1;
        int i = 1;
        while(x+i < this.state.sizeX && this.state.board[x + i][y] == 
          this.state.board[x][y]) {
          count++;
          i++;
      }
        i = 1;
        while(x - i >= 0 && this.state.board[x - i][y] == this.state.board[x][y])
      {
          count++;
          i++;
      }
        if(count >= winningLength)
      {
          //System.out.println("Horizontal win");
          return true;
      }
        else
      {
          return false;
      }
    }
	}
	
	private boolean checkVertical(int x, int y)
	{
		if (x < 0 || y < 0 || x >= this.state.sizeX
				|| y >= this.state.sizeY)
		{
			return false;
		}
		else
		{
		    int count = 1;
		    int i = 1;
		    while(y+i < this.state.sizeY && this.state.board[x][y+i] == 
          this.state.board[x][y]) {
			    count++;
			    i++;
			}
		    i = 1;
		    while(y-i >= 0 && this.state.board[x][y-i] == this.state.board[x][y])
			{
			    count++;
			    i++;
			}
		    if(count >= winningLength)
			{
			    //System.out.println("Vertical win");
			    return true;
			}
		    else
			{
			    return false;
			}
		}
	}
	

	private boolean isOver(TicTacToeMove move)
	{
		int x = move.getX();
		int y = move.getY();
		if ((moveNum == 9) && !(this.checkAntiDiagonal(x, y) || this.checkDiagonal(x, y)
        || this.checkHorizontal(x, y) || this.checkVertical(x, y))) {
      winner = -1;
      return true;
    }

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
	{
	    //System.out.println("winner: " + winner);
	    //System.out.println("mov Num: " + moveNum);
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

  public void print() {
    state.print();
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
				//this.state.print();
			}
			this.moveNum++;
			return 0;
		} else
		{
			return 1;
		}
	}

}
