public abstract class AbstractNeuron implements Comparable<AbstractNeuron> {
  public int index;
  // private Double value;
  protected String name;

  public int compareTo(AbstractNeuron other_) {
    return Integer.compare(this.index, other_.index);
  }

  public void setIndex(int index_) {
    index = index_;
  }

  public String selfDescription() {
    String description = name + " " + index;
    return description;
  }

  public void signal(Double value_) {}

  public abstract String printLinks();
  public abstract void voice();
  public abstract void setValue(Double value_);
  public abstract Double getValue(); // produces the output
  public abstract void updateWeights(Double learningRate_);
  public abstract void initialize(); // purge
  public abstract void mutate(Double amplitude_); // for Genetic Agl
  public abstract void updateGradients(Double feedBack_); // for backProp Alg
  public abstract void propagate();
  public abstract void addParent(AbstractNeuron parent_, Double weight_);
  public abstract void addParent(AbstractNeuron parent_);
  public abstract void addChild();
  public abstract void setUnknown();
}