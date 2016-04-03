public class IdentityNeuron extends AbstractNeuron {

  private static IdentityNeuron firstInstance = null;
  
  private IdentityNeuron() {
    name = "IdentityNeuron";
    // this a private constructor of an identity Neuron
  }

  public static IdentityNeuron getInstance() {
    if (firstInstance == null) {
      firstInstance = new IdentityNeuron();
    }
    return firstInstance;
  }

  @Override
  public void setUnknown() {}

  @Override
  /**
   * Useless function to test functionality
   */
  public void voice() {
    System.out.println(name);
  }

  @Override
  /**
   * Sets the value of the neuron to a give value
   *
   * @param      value_  Value that we want to set the Neuron to
   */
  public void setValue(Double value_) {}

  @Override
  /**
   * Retrieve the output value from the Neuron
   *
   * @return     The output value
   */
  public Double getValue() {
    return 1.; // identity Neuron always outputs 1.
  } // produces the output

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
  public String printLinks() {
    String empty = "";
    return empty;
  }

  @Override
  public void addChild() {}

  @Override
  /**
   * Propagates the gradients for weights in the network
   */
  public void propagate() {};

}

