public class Signal implements Applicable {
  private Double value;

  public Signal(Double value_) {
    value = value_;
  }

  public void action(AbstractNeuron neuron_) {
    neuron_.signal(value);
  }
}