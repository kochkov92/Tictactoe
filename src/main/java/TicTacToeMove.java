public class TicTacToeMove extends Move
{
	private int player;
	private int x,y;
	
	public TicTacToeMove(int player, int x, int y)
	{
		this.player = player;
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getPlayer()
	{
		return player;
	}
}
