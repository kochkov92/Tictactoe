import java.util.*;

public abstract class NeuralNetwork extends AbstractPlayer
{
	int nextIndex = 0;
	Vector <Neuron> input = new Vector <Neuron>();
	Vector <Neuron> hidden = new Vector <Neuron>();
	Vector <Neuron> output = new Vector <Neuron>();
	Move nextMove;
	
	
	public abstract void saveBrain();
	public abstract void loadBrain();
	
	protected abstract void computeMove();
	
	public Move getMove()
	{
		return nextMove;
	}
	
	
	
}
