import java.util.*;
// import java.util.Collections;
import java.io.*;
import java.lang.*;
import java.time.*;
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
      newNeuron = new OutputNeuronP();
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
      Vector<Double> answer = convertLabel(trainLabels.get(i));
      iterateAll(new SetUnknown());
      setInputs(validationAttributes.get(i));
      for (int k = 0; k < outputLayer.size(); ++k) {
        error += (answer.get(k) - outputLayer.get(k).getValue())
          * (answer.get(k) - outputLayer.get(k).getValue());
      }
    }
    return error;
  }

  private void decreaseMomentum(Double factor_) {
    Signal momentumDecrease = new Signal(factor_);
    iterateAll(momentumDecrease);
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
    Vector<Integer> order = new Vector<Integer>();
    for (int i = 0; i < trainAttributes.size(); ++i) {
      order.add(i);
    }
    for (int i = 0; i < iterations_; ++i) {
      // disableRandom(1, 0); // this is a dropOut
      Double error = 0.; // do I need that?
      int mistakes = 0;
      Collections.shuffle(order);
      for (int j = 0; j < trainAttributes.size(); ++j) {
        iterateAll(new SetUnknown());
        setInputs(trainAttributes.get(order.get(j)));
        Double distance = 0.;
        if (!digitPrediction().equals(trainLabels.get(order.get(j)))) {
          mistakes++;
          // System.out.print(j + " ");
        }
        backProp(convertLabel(trainLabels.get(order.get(j)))); // old update
        updateWeightsAll(learningRate_); // this is artificial /// (1 + Math.sqrt(Math.sqrt(i)))
        for (int k = 0; k < outputLayer.size(); ++k) {
          error += (convertLabel(trainLabels.get(order.get(j))).get(k) - outputLayer.get(k).getValue()) * 
                   (convertLabel(trainLabels.get(order.get(j))).get(k) - outputLayer.get(k).getValue());
        }
      }
      saveStats(error, i, mistakes);
      System.out.println("Error at epoch " + i + " is " + error + " " + mistakes);
    }
  }

  public void disableRandom(double num_, int layer_) {
    Iterator<AbstractNeuron> layerIter;
    layerIter = hiddenLayers.get(layer_).iterator();
    Double rate = 1. - num_ / hiddenLayers.get(layer_).size();
    while (layerIter.hasNext()) {
      if (1. * Math.random() > rate) {
        AbstractNeuron current = layerIter.next();
        System.out.println("I disabled neuron # " + current.index);
        current.setDisabled(true);
      }
      else {
        layerIter.next();
      }
    }
  }

  public void saveStats(Double error_, int epoch_, int mistakes_) {
    try {
      Clock myClock = Clock.systemDefaultZone();
      PrintWriter writer = new PrintWriter(new FileOutputStream(new File("progress.txt"), true));
      writer.println(epoch_ + " " + error_ + " " + mistakes_ + " " + myClock.instant().now());
      writer.close();
    }
    catch(FileNotFoundException ex) {
      System.out.println("Unable to open file");                
    }
    // catch(UnsupportedEncodingException ex) {}
  }

  public static void main(String[] args) {
    // NeuralNetworkP digitRecognizer = new NeuralNetworkP(1);
    // digitRecognizer.loadValidationData("data/validation.csv");
    // digitRecognizer.buildConnectedNetwork(784, 20, 10);
    // digitRecognizer.loadData("data/train1.csv");
    // digitRecognizer.trainBackPropDigits(0.015, 20);
    // digitRecognizer.saveNetwork("brain40Nodes.txt");

    NeuralNetworkP digitRecognizer = new NeuralNetworkP(2);
    // digitRecognizer.loadValidationData("data/validation.csv");
    // digitRecognizer.buildConnectedNetwork(784, 40, 10);
    digitRecognizer.loadNetwork("bigTrainBrain9.txt");
    // digitRecognizer.loadData("data/30kTrain.csv");
    digitRecognizer.loadData("data/1kTest.csv");
    digitRecognizer.trainBackPropDigits(0.0000, 6);
    // digitRecognizer.trainBackPropDigits(0.001, 10);
    digitRecognizer.saveNetwork("bigTrainBrain10.txt");
  }

}