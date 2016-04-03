import java.util.*;
public class InputNeuron extends AbstractNeuron {
  // constants
  public static final double WEIGHT_AMPLITUDE = 1.;
  // class variables
  boolean valueKnown;
  Double value;

  // class methods
  public InputNeuron() {
    valueKnown = false;
    name = "InputNeuron";
  }

  @Override
  public void setUnknown() {
    valueKnown = false;
  }

  @Override
  /**
   * Useless function to test functionality
   */
  public void voice() {
    System.out.println(name);
  }

  @Override
  /**
   * Adds a parent to the Neuron, weight is randomized
   *
   * @param      parent_  Reference to the parent
   */
  public void addParent(AbstractNeuron parent_) {}

  @Override
  /**
   * Adds a parent to the Neuron with specified weight
   *
   * @param      parent_  Reference to the parent
   * @param      weight_  Initial weight
   */
  public void addParent(AbstractNeuron parent_, Double weight_) {}

  @Override
  public void addChild() {}

  @Override
  /**
   * Propagates the gradients for weights in the network
   */
  public void propagate() {};

  @Override
  /**
   * Sets the value of the neuron to a give value
   *
   * @param      value_  Value that we want to set the Neuron to
   */
  public void setValue(Double value_) {
    value = value_;
    valueKnown = true;
  }

  @Override
  /**
   * Retrieve the output value from the Neuron
   *
   * @return     The output value
   */
  public Double getValue() {
    if (valueKnown) {
      return value;
    }
    System.out.println("Can't give value, because I was not initiated");
    return 0.;
  }
  
  @Override
  public String printLinks() {
    String empty = "";
    return empty;
  }
  
  @Override
  /**
   * Changes the weights of the Neuron, main learning procedure
   *
   * @param      learningRate_  The step in the weight space
   */
  public void updateWeights(Double learningRate_) {}

  @Override
  public void initialize() {}

  @Override
  public void mutate(Double amplitude_) {} // for Genetic Agl

  @Override
  public void updateGradients(Double feedBack_) {} // for backProp Alg

}