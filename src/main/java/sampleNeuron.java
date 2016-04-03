import java.util.*;
public class SimpleNeuron extends AbstractNeuron {
  // constants
  public static final double WEIGHT_AMPLITUDE = 1.;
  // class variables
  boolean valueKnown;
  Double value;
  Vector<Double> weights;
  Vector<Double> gradients;
  Vector<AbstractNeuron> parents;

  // class methods
  public SimpleNeuron(AbstractNeuron identity_) {
    weights = new Vector<Double>();
    parents = new Vector<AbstractNeuron>();
    gradients = new Vector<Double>();
    valueKnown = false;
    addParent(identity_);
    name = "SimpleNeuron";
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
    System.out.print("I have total of ");
    System.out.print(weights.size());
    System.out.print(" weights!");
  }

  @Override
  /**
   * Adds a parent to the Neuron, weight is randomized
   *
   * @param      parent_  Reference to the parent
   */
  public void addParent(AbstractNeuron parent_) {
    addParent(parent_, WEIGHT_AMPLITUDE * Math.random() - WEIGHT_AMPLITUDE / 2.);
  }

  @Override
  /**
   * Adds a parent to the Neuron with specified weight
   *
   * @param      parent_  Reference to the parent
   * @param      weight_  Initial weight
   */
  public void addParent(AbstractNeuron parent_, Double weight_) {
    parents.add(parent_);
    weights.add(weight_);
    gradients.add(0.);
  }

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
    computeValue();
    return value;
  }

  @Override
  /**
   * Changes the weights of the Neuron, main learning procedure
   *
   * @param      learningRate_  The step in the weight space
   */
  public void updateWeights(Double learningRate_) {
    // for (int i = 0; i < weights.size(); ++i) {
    //   System.out.print(weights.get(i));
    //   System.out.print(" ");
    // }
    // System.out.println();
    for (int i = 0; i < weights.size(); ++i) {
      weights.set(i, weights.get(i) + gradients.get(i) * learningRate_);
    }
    // for (int i = 0; i < weights.size(); ++i) {
    //   System.out.print(weights.get(i));
    //   System.out.print(" ");
    // }
    // System.out.println();
  }

  @Override
  public void initialize() {}

  @Override
  public void mutate(Double amplitude_) {} // for Genetic Agl

  @Override
  public void updateGradients() {}

  public void updateGradients(Double desiredOutput_) {
    for (int i = 0; i < parents.size(); ++i) {
      gradients.set(i, parents.get(i).getValue() * desiredOutput_);
    }
  }

  private void computeValue() {
    Double temp = 0.;
    for (int i = 0; i < parents.size(); ++i) {
      temp += (parents.get(i)).getValue() * weights.get(i);
    }
    value = temp;
    valueKnown = true;
  }

  public static void main(String[] args) {
    IdentityNeuron identityNeuron = IdentityNeuron.getInstance();
    SimpleNeuron test = new SimpleNeuron(identityNeuron);
    test.voice();
  }

  @Override
  /**
   * Adds a reference to a child
   *
   * @param      child_  Reference to a child Neuron
   */
  public void addChild(AbstractNeuron child_) {}
}