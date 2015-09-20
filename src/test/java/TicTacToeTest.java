import static org.junit.Assert.*;

import org.junit.Test;

public class TicTacToeTest
{

	@Test
	public void testGetBoard()
	{
		
		TicTacToe game = new TicTacToe(3,3);
		TicTacToeState state = new TicTacToeState(3,3);
		
		assertTrue(game.getState().equals(state));
	}
	
	@Test
	public void testUpdate()
	{
		TicTacToe game = new TicTacToe(3,3);
		TicTacToeState state = new TicTacToeState(3,3);
		
		TicTacToeMove move = new TicTacToeMove(1,1,1);
		
		state.board[1][1] = 1;
		
		game.update(move);
		
		TicTacToeState state2 = (TicTacToeState)game.getState();	
		
		assertTrue(state2.equals(state));
		assertTrue(state.equals(state2));
		
	}
	
	@Test
	public void testGetTurn()
	{
		TicTacToe game = new TicTacToe(3,3);
		TicTacToeMove move = new TicTacToeMove(1,1,1);
		
		assertTrue(game.getTurn()==1);
		
		game.update(move);
		
		assertTrue(game.getTurn() == 2);
		
	}
	
	@Test
	/** Tests for horizontal winner condition
	 * 
	 */
	public void testGetWinnerH()
	{
		TicTacToe game = new TicTacToe(3,3);
		TicTacToeMove move1 = new TicTacToeMove(1,0,0);
		TicTacToeMove move2 = new TicTacToeMove(2,0,1);
		TicTacToeMove move3 = new TicTacToeMove(1,1,0);
		TicTacToeMove move4 = new TicTacToeMove(2,1,1);
		TicTacToeMove move5 = new TicTacToeMove(1,2,0);
		
		game.update(move1);
		game.update(move2);
		game.update(move3);
		game.update(move4);
		game.update(move5);
		
		assertTrue(game.getWinner() == 1);
	}
	
	@Test
	/** Tests for vertical winner condition
	 * 
	 */
	public void testGetWinnerV()
	{
		TicTacToe game = new TicTacToe(3,3);
		TicTacToeMove move1 = new TicTacToeMove(1,0,0);
		TicTacToeMove move2 = new TicTacToeMove(2,2,1);
		TicTacToeMove move3 = new TicTacToeMove(1,0,1);
		TicTacToeMove move4 = new TicTacToeMove(2,1,1);
		TicTacToeMove move5 = new TicTacToeMove(1,0,2);
		
		game.update(move1);
		game.update(move2);
		game.update(move3);
		game.update(move4);
		game.update(move5);
		
		assertTrue(game.getWinner() == 1);
	}
	
	
	@Test
	/** Tests for diagonal winner condition
	 * 
	 */
	public void testGetWinnerD()
	{
		TicTacToe game = new TicTacToe(3,3);
		TicTacToeMove move1 = new TicTacToeMove(1,0,1);
		TicTacToeMove move2 = new TicTacToeMove(2,0,0);
		TicTacToeMove move3 = new TicTacToeMove(1,1,0);
		TicTacToeMove move4 = new TicTacToeMove(2,1,1);
		TicTacToeMove move5 = new TicTacToeMove(1,2,0);
		TicTacToeMove move6 = new TicTacToeMove(2,2,2);
		
		game.update(move1);
		game.update(move2);
		game.update(move3);
		game.update(move4);
		game.update(move5);
		game.update(move6);
		
		assertTrue(game.getWinner() == 2);
	}
	
	@Test
	public void testNoWinner()
	{
		TicTacToe game = new TicTacToe(3,3);
		
		assertTrue(game.getWinner() == 0);
	}

}
