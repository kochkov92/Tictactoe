import static org.junit.Assert.*;

import org.junit.Test;

public class TicTacToeStateTest
{

	@Test
	public void testClone()
	{
		TicTacToeState mystate = new TicTacToeState(4, 4);
		mystate.board[0][0] = 1;
		mystate.board[1][1] = 1;
		mystate.board[1][3] = 1;
		mystate.board[2][2] = 1;
		TicTacToeState newstate;
		newstate = mystate.clone();
		
		assertTrue(mystate.equals(newstate));

	}
	
	@Test
	public void testInequalSize()
	{
		TicTacToeState state1 = new TicTacToeState(3,3);
		TicTacToeState state2 = new TicTacToeState(3,4);
		
		assertFalse(state1.equals(state2));
		assertFalse(state2.equals(state1));
	
	}
	
	@Test
	public void testEqual()
	{
		TicTacToeState state1 = new TicTacToeState(3,3);
		TicTacToeState state2 = new TicTacToeState(3,3);
		
		
		state1.board[0][0] = 1;
		state2.board[0][0] = 1;
		
		
		assertTrue(state1.equals(state2));
		assertTrue(state2.equals(state1));
		
	}
	
	
	
	

}