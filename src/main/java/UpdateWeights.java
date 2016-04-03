public interface Applicable {
  public void action(AbstractNeuron neuron_);
}

public class UpdateWeights implements Applicable {
  Double learningRate;
  public UpdateWeights(Double learningRate_) {
    learningRate = learningRate_;
  }
  public void action(AbstractNeuron neuron_) {
    neuron_.UpdateWeights(learningRate);
  }
}