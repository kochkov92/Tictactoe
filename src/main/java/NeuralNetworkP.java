import java.util.*;
import java.io.*;
import java.lang.*;
public class NeuralNetworkP extends NeuralNetwork {
  Vector<Vector<Double> > validationAttributes;
  Vector<Double> validationLabels;

  public NeuralNetworkP(int numHiddenLayers_) {
    super(numHiddenLayers_);
    validationLabels = new Vector<Double>();
    validationAttributes = new Vector<Vector<Double> >();
  }

  public void loadValidationData(String fileName_) {
    DataReader myReader = new DataReader();
    myReader.readFile(fileName_);
    validationAttributes = myReader.getAttributes();
    validationLabels = myReader.getLabels();
  }

  @Override
  protected AbstractNeuron createNeuron(String line_) {
    AbstractNeuron newNeuron;
    Scanner scan = new Scanner(line_);
    String type = scan.next();
    int index = scan.nextInt();
    if (type.equals("InputNeuron")) {
      newNeuron = new InputNeuron();
    }
    else if (type.equals("Neuron")) {
      newNeuron = new NeuronP();
    }
    else if (type.equals("PerceptronNeuron")) {
      newNeuron = new PerceptronNeuron(identityNeuron);
    }
    else if (type.equals("OutputNeuron")) {
      newNeuron = new OutputNeuron();
    }
    else {
      System.out.println("Creating usual Neuron, don't have " + type);
      newNeuron = new Neuron(identityNeuron);
    }
    newNeuron.setIndex(index);
    return newNeuron;
  }

  private Double errorOnValidationSet() {
    Double error = 0.;
    for (int i = 0; i < validationAttributes.size(); ++i) {
      error += (digitPrediction(validationAttributes.get(i)) - validationLabels.get(i)) *
        (digitPrediction(validationAttributes.get(i)) - validationLabels.get(i));
    }
    return error;
  }

  private void decreaseMomentum(Double factor_) {
    Signal momentumDecreas = new Signal(factor_);
    iterateAll(momentumDecreas);
  }


  @Override
  public void buildConnectedNetwork(int numAttributes_, int hiddenNeurons_,
                                    int outNeurons_) {
    identityNeuron = IdentityNeuron.getInstance();
    setIndex(identityNeuron);
    for (int i = 0; i < numAttributes_; ++i) {
      AbstractNeuron newNeuron = new InputNeuron();
      setIndex(newNeuron);
      inputLayer.add(newNeuron);
    }
    for (int i = 0; i < hiddenLayers.size(); ++i) {
      for (int j = 0; j < hiddenNeurons_; ++j) {
        AbstractNeuron newNeuron = new NeuronP(identityNeuron);
        setIndex(newNeuron);
        Iterator<AbstractNeuron> layerIter;
        if (i == 0) {
          layerIter = inputLayer.iterator();
        }
        else {
          layerIter = (hiddenLayers.get(i - 1)).iterator();
        }
        connectNeuron(newNeuron, layerIter);
        hiddenLayers.get(i).add(newNeuron);
      }
    }
    for (int i = 0; i < outNeurons_; ++i) {
      AbstractNeuron newNeuron = new OutputNeuron(identityNeuron);
      setIndex(newNeuron);
      Iterator<AbstractNeuron> layerIter;
      if (hiddenLayers.isEmpty()) {
        layerIter = inputLayer.iterator();
      }
      else {
        layerIter = hiddenLayers.lastElement().iterator();
      }
      connectNeuron(newNeuron, layerIter);
      outputLayer.add(newNeuron);
    }
  }

  @Override
  public void trainBackPropDigits(Double learningRate_, int iterations_) {
    Double lastValidationError = 0.;
    for (int i = 0; i < iterations_; ++i) {
      Double error = 0.; // do I need that?
      int mistakes = 0;
      for (int j = 0; j < trainAttributes.size(); ++j) {
        iterateAll(new SetUnknown());
        setInputs(trainAttributes.get(j));
        Double distance = 0.;
        if (!digitPrediction().equals(trainLabels.get(j))) {
          mistakes++;
        }
        backProp(convertLabel(trainLabels.get(j))); // old update
        updateWeightsAll(learningRate_ / (1 + Math.sqrt(Math.sqrt(i)))); // this is artificial
        if (j % 300 == 0) {
          error = errorOnValidationSet();
          if (lastValidationError < error) {
            decreaseMomentum(5.);
          }
          lastValidationError = error;
          System.out.println("Error at validation " + error);
        }
        // for (int k = 0; k < outputLayer.size(); ++k) {
        //   error += (convertLabel(trainLabels.get(j)).get(k) - outputLayer.get(k).getValue()) * 
        //            (convertLabel(trainLabels.get(j)).get(k) - outputLayer.get(k).getValue());
        // }
      }
      System.out.println("Error at epoch " + i + " is " + error + " " + mistakes);
    }
  }

  public static void main(String[] args) {
    // NeuralNetworkP digitRecognizer = new NeuralNetworkP(1);
    // digitRecognizer.loadValidationData("data/validation.csv");
    // digitRecognizer.buildConnectedNetwork(784, 20, 10);
    // digitRecognizer.loadData("data/train1.csv");
    // digitRecognizer.trainBackPropDigits(0.015, 20);
    // digitRecognizer.saveNetwork("brain40Nodes.txt");

    NeuralNetworkP digitRecognizer = new NeuralNetworkP(0);
    digitRecognizer.loadValidationData("data/validation.csv");
    digitRecognizer.loadNetwork("firstBrain.txt");
    digitRecognizer.loadData("data/train1.csv");
    digitRecognizer.trainBackPropDigits(0.001, 40);
  }

}