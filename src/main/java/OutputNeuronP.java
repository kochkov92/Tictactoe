import java.util.*;
public class OutputNeuronP extends AbstractNeuron {
  // constants
  public static final double WEIGHT_AMPLITUDE = 1.;
  // class variables
  boolean valueKnown; // do we know the value of the neuron?
  boolean disabled;
  int numChildren; // needed for delta updates, to know when we got all parts
  int deltaUpdates; // how many childer already contributed their parts
  Double value; // value of the artificial neuron
  Double argument; // the weighted sum, can be usefull for deriv value
  Double delta; // the crucial part of backProp,
  int gradUpdates;
  int batchUpdate;
  Double viscosity;
  Double momentumRate;
  Vector<Double> weights; // vector of weights
  Vector<Double> gradients; // gradients with respect to weights
  Vector<Double> momentum;
  Vector<AbstractNeuron> parents; // references to parent neurons

  // class methods
  public OutputNeuronP(AbstractNeuron identity_) {
    this();
    addParent(identity_);
    name = "OutputNeuron";
  }

  public OutputNeuronP() {
    numChildren = 0;
    deltaUpdates = 0;
    batchUpdate = 30;
    momentumRate = 0.2;
    viscosity = 0.;
    weights = new Vector<Double>();
    parents = new Vector<AbstractNeuron>();
    gradients = new Vector<Double>();
    momentum = new Vector<Double>();
    valueKnown = false;
    disabled = false;
    name = "OutputNeuron";
  }

  private Double activation(Double argument_) {
    return Math.tanh(argument_);
    // return 2. / ( 1 + Math.exp(-1. * argument_)) - 1; // for sigmoidal act. function
  }

  public Double getDeriv() {
    return 1 - value * value;
    // return 2 * value * (1 - value); // for sigmoidal activation function
  }

  @Override
  public void setUnknown() {
    if (!disabled) {
      valueKnown = false;
    }
  }

  @Override
  public void setDisabled(boolean disabled_) {
    if (disabled_) {
      disabled = disabled_;
      valueKnown = true;
      value = 0.;
      argument = 0.;
    }
    else {
      valueKnown = false;
    }
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
    momentum.add(0.);
    parent_.addChild();
  }

  @Override
  /**
   * Adds a reference to a child
   *
   * @param      child_  Reference to a child Neuron
   */
  public void addChild() {
    numChildren++;
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
    if (gradUpdates == batchUpdate) {
      for (int i = 0; i < weights.size(); ++i) {
        momentum.set(i, viscosity * momentum.get(i) + gradients.get(i) / gradUpdates);
        weights.set(i, weights.get(i) - learningRate_ * momentum.get(i));

        // momentum.set(i, momentum.get(i) + momentumRate * gradients.get(i) * 
        //   learningRate_ / gradUpdates); // update the cumulative momentum every iteration
        // weights.set(i, weights.get(i) - momentum.get(i) - 
        //   gradients.get(i) * learningRate_ / gradUpdates);
      }
      gradUpdates = 0;
      resetGradients();
    }
  }

  private void resetGradients() {
    for (int i = 0; i < gradients.size(); ++i) {
      gradients.set(i, 0.);
    }
  }

  @Override
  public void initialize() {}

  @Override
  public void mutate(Double amplitude_) {} // for Genetic Agl

  @Override
  public void updateGradients(Double feedBack_) {
    // delta = feedBack_ * getDeriv();
    gradUpdates++;
    delta = (value - feedBack_) * getDeriv();
    for (int i = 0; i < parents.size(); ++i) {
      gradients.set(i, gradients.get(i) + delta * parents.get(i).getValue());
    }
  }



  private void reduceMomentum(Double factor_) {
    for (int i = 0; i < momentum.size(); ++i) {
      momentum.set(i, momentum.get(i) / factor_);
    }
  }

  @Override
  public void signal(Double value_) {
    reduceMomentum(value_);
  }

  /**
   * Propagates the gradients for weights in the network
   */
  @Override
  public void propagate() {
    for(int i = 0; i < parents.size(); ++i) {
      parents.get(i).updateGradients(delta * weights.get(i));
    }
  }

  private void computeValue() {
    argument = 0.;
    for (int i = 0; i < parents.size(); ++i) {
      argument += (parents.get(i)).getValue() * weights.get(i);
    }
    valueKnown = true;
    value = activation(argument);
  }

  @Override
  public String printLinks() {
    String out = weights.get(0).toString();
    for (int i = 1; i < weights.size(); ++i) {
      out += " " + parents.get(i).index + " ";
      out += weights.get(i);
    }
    return out;
  }

  public static void main(String[] args) {
    IdentityNeuron identityNeuron = IdentityNeuron.getInstance();
    Neuron test = new Neuron(identityNeuron);
    test.voice();
  }
}