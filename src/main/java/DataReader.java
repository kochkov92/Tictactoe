import java.util.*;
import java.io.*;

public class DataReader {
  Vector<Double> labels; // vector with correct labels
  Vector<Vector<Double> > attributes; // vector of correct attributes
  boolean hasLabels;
  boolean hasAttributes;

  /**
   * Initializes the reader
   */
  public DataReader() {
    labels = new Vector<Double>();
    attributes = new Vector<Vector<Double> >();
    hasLabels = false;
    hasAttributes = false;
  }


  /**
   * Passes labels that the Reader has in its posession
   *
   * @return     Vector of labels or NULL if has none
   */
  public Vector<Double> getLabels() {
    if (hasLabels){
      return labels;
    }
    return null;
  }

  /**
   * Passes the training Attributes
   *
   * @return     Vector of training examples or NULL
   */
  public Vector<Vector<Double> > getAttributes() {
    if (hasAttributes) {
      return attributes;
    }
    return null;
  }

  /**
   * Computes the number of instances in the file based on first line
   *
   * @param      fileName  Name of the file, String
   *
   * @return     integer, number of attributes, considering last one to be
   *             the output
   */   
  private int getNumAttributes(String fileName) {
    try {
      FileReader fileReader = new FileReader(fileName); // opens the file
      BufferedReader bufferedReader = new BufferedReader(fileReader); // wraps it
      String line = null;
      line = bufferedReader.readLine(); // here we read the first line
      int counter = 0; // counter of number of elements per line
      Scanner scan = new Scanner(line); // to avoid spaces and punctuation
      scan.useDelimiter(","); // that's for CVS files
      while(scan.hasNext()) {
        scan.nextDouble();
        counter++; // incriment after every meaningful element
      }
      bufferedReader.close();
      return counter - 1; // last one was the output/label
    } // have to catch them all
    catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + fileName + "'");                
    }
    catch(IOException ex) {
      System.out.println("Error reading file '" + fileName + "'");
    }
    return 0;
  }

  /**
   * Reads the file with training instances and parses it
   *
   * @param      fileName  String, name of the file
   */
  public void readFile(String fileName) {
    int numFeatures = getNumAttributes(fileName); // to know how to read a file
    try {
      FileReader fileReader = new FileReader(fileName);

      Scanner scanLine = new Scanner(fileReader);
      int numInstances = 0;
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while (scanLine.hasNextLine()) {
        String line = scanLine.nextLine(); //bufferedReader.readLine();
        Scanner scan = new Scanner(line);
        scan.useDelimiter(",");
        attributes.add(new Vector<Double>());
        labels.add(scan.nextDouble()); // comment out if label is last
        for(int i = 0; i < numFeatures; ++i) {
          attributes.get(numInstances).add(scan.nextDouble());
        }
        // labels.add(scan.nextDouble()); // 
        numInstances++;
      }

      // Scanner scan = new Scanner(fileReader);
      // scan.useDelimiter(",");
      // int numInstances = 0; // counts the number of data Instances
      // while(scan.hasNextLine()) {
      //   attributes.add(new Vector<Double>());
      //   labels.add(scan.nextDouble()); // comment out if label is last
      //   for(int i = 0; i < numFeatures; ++i) {
      //     attributes.get(numInstances).add(scan.nextDouble());
      //   }
      //   // labels.add(scan.nextDouble()); // uncomment if label is last
      //   numInstances++;
      // }
      hasAttributes = true; // so that we can return them
      hasLabels = true; // so that we can return them
    }
    catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + fileName + "'");                
    }
    // catch(IOException ex) {
    //   System.out.println("Error reading file '" + fileName + "'");
    // }
  }

  public static void main(String[] args) {
    DataReader testReader = new DataReader();
    testReader.readFile("test.txt");
  }

}