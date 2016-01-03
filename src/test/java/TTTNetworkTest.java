import static org.junit.Assert.*;

import org.junit.Test;

public class TTTNetworkTest
{

	@Test
	public void testPrint()
	{
		TTTNetwork test = new TTTNetwork(1,3,1,3);
		test.print();
	}
	
	@Test
	public void testReceiveState()
	{
		int boardSize = 3;
		TTTNetwork test = new TTTNetwork(1,boardSize,1,3);
		TicTacToeState state = new TicTacToeState(boardSize,boardSize);
		test.receiveState(state);
		//test.print();
		
		for(int i = 0; i < boardSize*boardSize; i++)
		{
			assertTrue(test.input.get(i).getValue() == 1.0);
		}
		
		
		//setting board values to nonsense for testing purposes
		state.board[0][0] = 0;
		state.board[0][1] = 1;
		state.board[0][2] = 2;
		state.board[1][0] = 3;
		state.board[1][1] = 4;
		state.board[1][2] = 5;
		state.board[2][0] = 6;
		state.board[2][1] = 7;
		state.board[2][2] = 8;
		
		test.receiveState(state);
		//test.print();
		for(int i =0; i<boardSize*boardSize; i++)
		{
			//System.out.println(i + ": " + test.input.get(i).getValue());
				assertTrue(test.input.get(i).getValue() == i + 1);
		}
		
	}
	

}
