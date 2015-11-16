import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeMoveTest
{

	@Test
	public void testMoveX()
	{
		TicTacToeMove move = new TicTacToeMove(1,2,3);
		assertTrue(move.getX()==2);
	}
	
	@Test
	public void testMoveY()
	{
		TicTacToeMove move = new TicTacToeMove(1,2,3);
		assertTrue(move.getY()==3);
	}

	@Test
	public void testMovePlayer()
	{
		TicTacToeMove move = new TicTacToeMove(1,2,3);
		assertTrue(move.getPlayer()==1);
	}	
	
}
