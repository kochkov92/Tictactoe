
public class TTTNetwork extends NeuralNetwork
{
	int boardSize;	//length or width of board
	
	
	public TTTNetwork(int boardSize, int hiddenLayers, int hiddenNeurons)
	{
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
		// TODO Auto-generated method stub

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
		output.get(0).getValue();

	}

}
