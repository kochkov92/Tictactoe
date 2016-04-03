public class NeuralNetworkP extends NeuralNetwork {

  public NeuralNetworkP(int numHiddenLayers_) {
    super(numHiddenLayers_);
  }

  public static void main(String[] args) {
    System.out.println("I don't need to import things anymore");
    NeuralNetworkP digitRecognizer = new NeuralNetworkP(1);
    digitRecognizer.buildConnectedNetwork(784, 20, 10);
    digitRecognizer.loadData("data/train1.csv");
    digitRecognizer.trainBackPropDigits(0.015, 10);
    // digitRecognizer.saveNetwork("brain40Nodes.txt");
  }

}