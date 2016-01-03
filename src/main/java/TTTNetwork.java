
public class TTTNetwork extends NeuralNetwork
{
	int boardSize;	//length or width of board
	
	/**
	 * Uses default of TANH for activation function
	 * @param boardSize length (or width) of board
	 * @param hiddenLayers number of hidden layers
	 * @param hiddenNeurons number of neurons per hidden layer
	 */
	public TTTNetwork(int playerNumber, int boardSize, int hiddenLayers, int hiddenNeurons)
	{
		this.playerNumber = playerNumber;
		this.boardSize = boardSize;
		
		//create input neurons
		for(int i = 0;i< boardSize*boardSize; i++)
		{
			Neuron inputNode = new Neuron(nextIndex,0,Neuron.TANH);
			inputNode.setValue(1);
			input.add(inputNode);
			nextIndex++;
		}
		
		for(int i =0; i<hiddenLayers; i++)
		{
			for(int j = 0; j<hiddenNeurons;j++)
			{
				Neuron node = new Neuron(nextIndex,i+1,Neuron.TANH);
				
				//fill in the parents from previous layer
				if(i == 0)	//previous layer is input
				{
					for(int k = 0;k<input.size();k++)
					{
						node.addParent(input.get(k), randomWeight());
					}
				}
				else	//previous layer is hidden
				{
					for(int k = 0; k<hidden.size();k++)
					{
						if(hidden.get(k).getLayer() == i)
						{
							node.addParent(hidden.get(k), randomWeight());
						}
					}
				}
				
				
				hidden.add(node);
				nextIndex++;
			}
			
		}
		
		Neuron outNode = new Neuron(nextIndex,hiddenLayers+1,Neuron.TANH);
		
		for(int i = 0; i<hidden.size();i++)
		{
			if(hidden.get(i).getLayer() == hiddenLayers)
			{
				outNode.addParent(hidden.get(i), randomWeight());
			}
		}
		
		output.add(outNode);
		nextIndex++;
	}
	
	/**
	 * 
	 * @return Random weight between [-0.5,0.5]
	 */
	private double randomWeight()
	{
		return 1*Math.random()-0.5;
	}
	
	public void print()
	{
		for(int i =0; i<input.size();i++)
		{
			input.get(i).print();
		}
		for(int i = 0; i<hidden.size();i++)
		{
			hidden.get(i).print();
		}
		for(int i = 0; i<output.size();i++)
		{
			output.get(i).print();
		}
	}
	
	
	@Override
	public void newMatch()
	{
	}
	
	
	@Override
	public void receiveState(GameState state)
	{
		if(state instanceof TicTacToeState)
		{
			receiveState((TicTacToeState)state);
		}
		else
		{
				System.out.println("Error: TTTNetwork Received non TTT Game state");
				return;
		}
	}
	
	/**
	 * Gets the current game state from the ref and updates the input nodes and computes the next move
	 * @param state
	 */
	private void receiveState(TicTacToeState state)
	{
		for(int i = 0; i< state.sizeX; i++)
		{
			for(int j = 0; j< state.sizeY;j++)
			{
				input.get(state.sizeY*i + j).setValue(state.board[i][j] + 1);
				
			}
		}
		
		for(int i = 0; i < hidden.size();i++)
		{
			hidden.get(i).setUnknown();
		}
		for(int i = 0; i < output.size();i++)
		{
			output.get(i).setUnknown();
		}
		
		
		computeMove();
		
		//if move is illegal
		//TODO: come up with better solution than choosing random moves
		//	probably some kind of concede? like change illegal moves to auto lose
		if(state.board[((TicTacToeMove)nextMove).getX()][((TicTacToeMove)nextMove).getY()] != 0)
		{
			nextMove = randomMove();
		}
		
	}
	

	private Move randomMove()
	{
		return new TicTacToeMove(playerNumber,(int)Math.floor(Math.random()*boardSize),(int)Math.floor(Math.random()*boardSize));
	}

	@Override
	public void saveBrain()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void loadBrain()
	{
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 *  Currently maps the -1 to +1 output of tanh linearly to the range 
	 *  	0 to boardSize^2 and floors.  each integer corresponds to an 
	 *  	enumeration of the spaces
	 */
	protected void computeMove()
	{
		double moveValue = output.get(0).getValue();
		moveValue = moveValue + 1;
		moveValue = boardSize*boardSize*moveValue/2;
		
		int x = (int)Math.floor(moveValue)/boardSize;
		int y = (int)Math.floor(moveValue)%boardSize;
		
		
		nextMove = new TicTacToeMove(playerNumber,x,y);
		//nextMove.printMove();
		//System.out.println(output.get(0).getValue());

	}

}
