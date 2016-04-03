import java.util.*;
import java.io.*;
import java.lang.*;
public class NeuralNetwork {
  // private 
  Vector<AbstractNeuron> inputLayer; // vector of input Neurons
  Vector<TreeSet<AbstractNeuron> > hiddenLayers; // struct of hidden neurons
  Vector<AbstractNeuron> outputLayer; // vector of output Neurons
  IdentityNeuron identityNeuron; // singleton Identity Neuron for threshold
  Vector<Vector<Double> > trainAttributes;
  Vector<Double> trainLabels;
  int lastNeuronIndex;

  public NeuralNetwork(int numHiddenLayers_) {
    lastNeuronIndex = 0;
    trainAttributes = new Vector<Vector<Double> >();
    trainLabels = new Vector<Double>();
    inputLayer = new Vector<AbstractNeuron>();
    outputLayer = new Vector<AbstractNeuron>();
    hiddenLayers = new Vector<TreeSet<AbstractNeuron> >();
    for (int i = 0; i < numHiddenLayers_; ++i) {
      hiddenLayers.add(new TreeSet<AbstractNeuron>());
    }
  }

  public void setIndex(AbstractNeuron neuron_) {
    neuron_.setIndex(lastNeuronIndex);
    lastNeuronIndex++;
  }

  public void buildPerceptron(int numAttributes_) {
    identityNeuron = IdentityNeuron.getInstance();
    setIndex(identityNeuron);
    for (int i = 0; i < numAttributes_; ++i) {
      AbstractNeuron newNeuron = new InputNeuron();
      setIndex(newNeuron);
      inputLayer.add(newNeuron);
    }
    AbstractNeuron newNeuron = new PerceptronNeuron(identityNeuron);
    setIndex(newNeuron);
    Iterator<AbstractNeuron> inputIter = inputLayer.iterator();
    connectNeuron(newNeuron, inputIter);
    outputLayer.add(newNeuron);
  }

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
        AbstractNeuron newNeuron = new Neuron(identityNeuron);
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

  private void backProp(Double target_) {
    outputLayer.get(0).updateGradients(target_);
    outputLayer.get(0).propagate();
    for (int i = hiddenLayers.size() - 1; i >= 0; i--) {
      Iterator<AbstractNeuron> layerIter = hiddenLayers.get(i).iterator();
      while (layerIter.hasNext()) {
        layerIter.next().propagate();
      }
    }
  }

  protected void backProp(Vector<Double> targets_) {
    for (int i = 0; i < outputLayer.size(); ++i) {
      outputLayer.get(i).updateGradients(targets_.get(i));
      outputLayer.get(i).propagate();
    }
    for (int i = hiddenLayers.size() - 1; i >= 0; i--) {
      Iterator<AbstractNeuron> layerIter = hiddenLayers.get(i).iterator();
      while (layerIter.hasNext()) {
        layerIter.next().propagate();
      }
    }
  }

  protected Vector<Double> convertLabel(Double label_) {
    Vector<Double> vecLabel = new Vector<Double>();
    for (int i = 0; i < outputLayer.size(); ++i) {
      vecLabel.add(-1.);
    }
    vecLabel.set(label_.intValue(), 1.);
    return vecLabel;
  }

  protected void updateWeightsAll(Double learningRate_) {
    Iterator<AbstractNeuron> outputIter = outputLayer.iterator();
    while (outputIter.hasNext()) {
      outputIter.next().updateWeights(learningRate_);
    }
    for (int i = hiddenLayers.size() - 1; i >= 0; i--) {
      Iterator<AbstractNeuron> layerIter = hiddenLayers.get(i).iterator();
      while (layerIter.hasNext()) {
        layerIter.next().updateWeights(learningRate_);
      }
    }
  }


  public void trainBackProp(Double learningRate_, int iterations_) {
    for (int i = 0; i < iterations_; ++i) {
      Double error = 0.;
      for (int j = 0; j < trainAttributes.size(); ++j) {
        setUnknownAll();
        setInputs(trainAttributes.get(j));
        outputLayer.get(0).getValue();
        backProp(trainLabels.get(j));
        updateWeightsAll(learningRate_);
        error += (trainLabels.get(j) - outputLayer.get(0).getValue()) * 
                 (trainLabels.get(j) - outputLayer.get(0).getValue());
        // System.out.println("Forward pass gives " + output);
      }
      System.out.println("Error at epoch " + i + " is " + error);
    }
  }

  protected Double digitPrediction(Vector<Double> inputVals_) {
    setUnknownAll();
    setInputs(inputVals_);
    Double max = outputLayer.get(0).getValue();
    Double maxIndex = 0.;
    for (int i = 1; i < outputLayer.size(); ++i) {
      if (outputLayer.get(i).getValue() > max) {
        maxIndex = 1. * i;
        max = outputLayer.get(i).getValue();
      }
    }
    return maxIndex;
  }

  protected Double digitPrediction() {
    Double max = outputLayer.get(0).getValue();
    Double maxIndex = 0.;
    for (int i = 1; i < outputLayer.size(); ++i) {
      if (outputLayer.get(i).getValue() > max) {
        maxIndex = 1. * i;
        max = outputLayer.get(i).getValue();
      }
    }
    return maxIndex;
  }

  public void trainBackPropDigits(Double learningRate_, int iterations_) {
    for (int i = 0; i < iterations_; ++i) {
      Double error = 0.;
      int mistakes = 0;
      for (int j = 0; j < trainAttributes.size(); ++j) {
        digitPrediction(trainAttributes.get(j));
        Double distance = 0.;
        for (int k = 0; k < outputLayer.size(); ++k) {
          distance += outputLayer.get(k).getValue() * convertLabel(trainLabels.get(j)).get(k);
        }
        if (!digitPrediction().equals(trainLabels.get(j))) {
          mistakes++;
        }
        backProp(convertLabel(trainLabels.get(j))); // old update
        updateWeightsAll(learningRate_ / (1 + Math.sqrt(Math.sqrt(i)))); // this is artificial
        for (int k = 0; k < outputLayer.size(); ++k) {
          error += (convertLabel(trainLabels.get(j)).get(k) - outputLayer.get(k).getValue()) * 
                   (convertLabel(trainLabels.get(j)).get(k) - outputLayer.get(k).getValue());
        }
      }
      System.out.println("Error at epoch " + i + " is " + error + " " + mistakes);
    }
  }

  public void trainClassBackProp(Double learningRate_, int iterations_) {
    int mistakes = 0;
    for (int i = 0; i < iterations_; ++i) {
      Double error = 0.;
      for (int j = 0; j < trainAttributes.size(); ++j) {
        setInputs(trainAttributes.get(j));
        if (outputLayer.get(0).getValue() * trainLabels.get(j) < 0) {
          backProp(trainLabels.get(j));
          updateWeightsAll(learningRate_);
          mistakes++;
          error += (trainLabels.get(j) - outputLayer.get(0).getValue()) * 
                   (trainLabels.get(j) - outputLayer.get(0).getValue());
          if (j == 3) {
            System.out.println(outputLayer.get(0).getValue() + "  val on inst " + j);
          }
        }
        // System.out.println("Forward pass gives " + output);
        setUnknownAll();
      }
      System.out.println("Mistakes made " + mistakes + " err " + error);
      mistakes = 0;
    }
  }

  public void iterateAll(Applicable action_) {
    iterate(inputLayer.iterator(), action_);
    for (int i = 0; i < hiddenLayers.size(); ++i) {
      iterate(hiddenLayers.get(i).iterator(), action_);
    }
    iterate(outputLayer.iterator(), action_);
  }

  public void iterate(Iterator<AbstractNeuron> it_, Applicable action_) {
    while (it_.hasNext()) {
      action_.action(it_.next());
    }
  }

  private void setUnknownAll() {
    setUnknown(inputLayer.iterator());
    for (int i = 0; i < hiddenLayers.size(); ++i) {
      setUnknown(hiddenLayers.get(i).iterator());
    }
    setUnknown(outputLayer.iterator());
  }

  private void setUnknown(Iterator<AbstractNeuron> it_) {
    while (it_.hasNext()) {
      it_.next().setUnknown();
    }
  }

  private Double predict(Vector<Double> input_) {
    setUnknownAll();
    setInputs(input_);
    return outputLayer.get(0).getValue();
  }

  public void savePredictions(String fileName_) {
    loadData(fileName_);
    try {
      PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
      for (int i = 0; i < trainAttributes.size(); ++i) {
        for (int j = 0; j < trainAttributes.get(i).size(); ++j) {
          writer.print(trainAttributes.get(i).get(j) + " ");
        }
        writer.println(predict(trainAttributes.get(i)));
      }
      writer.close();
    }
    catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + fileName_ + "'");                
    }
    catch(UnsupportedEncodingException ex) {}
  }

  public void generateTrainSet(int numSamples_) {
    for (int i = 0; i < numSamples_; ++i) {
      Vector<Double> data = new Vector<Double>();
      for (int j = 0; j < inputLayer.size(); ++j) {
        data.add(10. * Math.random());
      }
      if (data.get(3) * 3 - data.get(4) * 1.1 + data.get(1) * 12 > 0) {
        trainLabels.add(1.);
      }
      else {
        trainLabels.add(-1.);
      }
      trainAttributes.add(data);
    }
  }

  protected void setInputs(Vector<Double> attributes_) {
    if (attributes_.size() != inputLayer.size()) {
      System.out.println("Number of features doesn't match");
      return; // maybe throw an exception
    }
    for (int i = 0; i < inputLayer.size(); ++i) {
      inputLayer.get(i).setValue(attributes_.get(i));
    }
  }

  public void trainPerceptron(int numIterations_, Double learningRate_) {
    for (int i = 0; i < numIterations_; ++i) {
      int mistakes = 0;
      for (int j = 0; j < trainAttributes.size(); ++j) {
        setInputs(trainAttributes.get(j));
        if (outputLayer.get(0).getValue() * trainLabels.get(j) <= 0) {
          ((PerceptronNeuron)outputLayer.get(0)).updateGradients(trainLabels.get(j));
          outputLayer.get(0).updateWeights(learningRate_);
          mistakes++;
        }
        outputLayer.get(0).setUnknown();
      }
      System.out.print("On iteration ");
      System.out.print(i);
      System.out.print(" got ");
      System.out.print(mistakes);
      System.out.println(" mistakes");
    }
  }

  public void loadData(String fileName_) {
    DataReader myReader = new DataReader();
    myReader.readFile(fileName_);
    trainAttributes = myReader.getAttributes();
    trainLabels = myReader.getLabels();
  }

  protected AbstractNeuron createNeuron(String line_) {
    AbstractNeuron newNeuron;
    Scanner scan = new Scanner(line_);
    String type = scan.next();
    int index = scan.nextInt();
    if (type.equals("InputNeuron")) {
      newNeuron = new InputNeuron();
    }
    else if (type.equals("Neuron")) {
      newNeuron = new Neuron();
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

  private AbstractNeuron findNeuron(int index_) {
    Iterator<AbstractNeuron> iter = inputLayer.iterator();
    while (iter.hasNext()) {
      AbstractNeuron next = iter.next();
      if (next.index == index_) {
        return next;
      }
    }
    for (int i = 0; i < hiddenLayers.size(); ++i) {
      iter = hiddenLayers.get(i).iterator();
      while (iter.hasNext()) {
        AbstractNeuron next = iter.next();
        if (next.index == index_) {
          return next;
        }
      }
    }
    System.out.println("Didn't find Neuron with index " + index_);
    return null;
  }

  public void loadNetwork(String fileName_) {
    try {
      FileReader fileReader = new FileReader(fileName_);
      Scanner scanLine = new Scanner(fileReader);
      String line = scanLine.nextLine();
      Scanner scan = new Scanner(line);
      Vector<Integer> layersSizes = new Vector<Integer>(); // output, input hidd
      layersSizes.add(scan.nextInt()); // add the num of output neurons
      layersSizes.add(scan.nextInt()); //add the num of input neurons
      while (scan.hasNext()) {
        layersSizes.add(scan.nextInt());
        hiddenLayers.add(new TreeSet<AbstractNeuron>());
      }
      System.out.println("Got " + layersSizes.get(0) + " input neurons");
      scan.close();
      lastNeuronIndex = 0; // just to make sure that we don't mess indices up
      identityNeuron = IdentityNeuron.getInstance();
      setIndex(identityNeuron);
      for (int i = 1; i < layersSizes.size(); ++i) { // skip the output first
        for (int j = 0; j < layersSizes.get(i); ++j) {
          if (scanLine.hasNextLine()) {
            line = scanLine.nextLine(); // read properties of the neuron
            AbstractNeuron newNeuron = createNeuron(line);
            line = scanLine.nextLine(); // read its connections
            scan = new Scanner(line); // to parse connections
            if (scan.hasNext()) {
              Double threshold = scan.nextDouble();
              newNeuron.addParent(identityNeuron, threshold);
            }
            while (scan.hasNext()) {
              int parentIndex = scan.nextInt();
              Double parentWeight = scan.nextDouble();
              newNeuron.addParent(findNeuron(parentIndex), parentWeight);
            }
            if (i == 1) {
              inputLayer.add(newNeuron);
            }
            else {
              hiddenLayers.get(i - 2).add(newNeuron);
            }
          }
        }
      }
      // now finish up with the output neurons
      for (int i = 0; i < layersSizes.get(0); ++i) {
        if (scanLine.hasNextLine()) {
          line = scanLine.nextLine(); // read properties of the neuron
          AbstractNeuron newNeuron = createNeuron(line);
          line = scanLine.nextLine(); // read its connections
          scan = new Scanner(line); // to parse connections
          Double threshold = scan.nextDouble();
          newNeuron.addParent(identityNeuron, threshold);
          while (scan.hasNext()) {
            int parentIndex = scan.nextInt();
            Double parentWeight = scan.nextDouble();
            newNeuron.addParent(findNeuron(parentIndex), parentWeight);
          }
          outputLayer.add(newNeuron);
        }
      }
    }
    catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + fileName_ + "'");                
    }
  }

  public void saveNetwork(String fileName_) {
    try {
      PrintWriter writer = new PrintWriter(fileName_, "UTF-8");
      writer.print(outputLayer.size() + " ");
      writer.print(inputLayer.size());
      for (int i = 0; i < hiddenLayers.size(); ++i) {
        writer.print(" " + hiddenLayers.get(i).size());
      }
      writer.print("\n");
      Iterator<AbstractNeuron> iter = inputLayer.iterator();
      while (iter.hasNext()) {
        AbstractNeuron currentNeuron = iter.next();
        writer.println(currentNeuron.selfDescription());
        writer.println(currentNeuron.printLinks());
      }
      for (int i = 0; i < hiddenLayers.size(); ++i) {
        iter = hiddenLayers.get(i).iterator();
        while (iter.hasNext()) {
          AbstractNeuron currentNeuron = iter.next();
          writer.println(currentNeuron.selfDescription());
          writer.println(currentNeuron.printLinks());
        }
      }
      iter = outputLayer.iterator();
      while (iter.hasNext()) {
        AbstractNeuron currentNeuron = iter.next();
        writer.println(currentNeuron.selfDescription());
        writer.println(currentNeuron.printLinks());
      }
      writer.close();
    }
    catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + fileName_ + "'");                
    }
    catch(UnsupportedEncodingException ex) {}
  }

  protected void connectNeuron(AbstractNeuron theNeuron_, 
                             Iterator<AbstractNeuron> parentsIt_) {
    while (parentsIt_.hasNext()) {
      theNeuron_.addParent(parentsIt_.next());
    }
  }

  public static void main(String[] args) {
    // NeuralNetwork digitRecognizer = new NeuralNetwork(0);
    // // digitRecognizer.loadNetwork("secondBrain.txt");
    // digitRecognizer.loadNetwork("firstBrain.txt");
    // digitRecognizer.loadData("data/train1.csv");
    // digitRecognizer.trainBackPropDigits(0.001, 40);
    // digitRecognizer.saveNetwork("thirdBrain.txt");

    NeuralNetwork digitRecognizer = new NeuralNetwork(1);
    digitRecognizer.buildConnectedNetwork(784, 40, 10);
    digitRecognizer.loadData("data/train1.csv");
    digitRecognizer.trainBackPropDigits(0.015, 200);
    digitRecognizer.saveNetwork("brain40Nodes.txt");

    // digitRecognizer.buildConnectedNetwork(10, 1, 10);
    // digitRecognizer.loadData("test2.txt");
    // 2D function fitting test
    // 
    // NeuralNetwork testNetwork = new NeuralNetwork(2);
    // testNetwork.buildConnectedNetwork(2, 20, 1);
    // testNetwork.loadData("function2D.txt");
    // testNetwork.trainBackProp(0.005, 500);
    // testNetwork.savePredictions("test2D.txt");


    // first backprop test
    // NeuralNetwork testNetwork = new NeuralNetwork(1);
    // testNetwork.buildConnectedNetwork(20, 10);
    // testNetwork.loadData("test.txt");
    // testNetwork.trainClassBackProp(0.01, 40);

  

    // Perceptron test
    // NeuralNetwork testNetwork = new NeuralNetwork(0);
    // testNetwork.buildPerceptron(20);
    // testNetwork.trainPerceptron(10, 0.001);

    System.out.println("I'm still alive");
  }
}