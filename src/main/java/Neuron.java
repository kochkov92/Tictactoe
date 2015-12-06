import java.util.*;
import java.lang.Math;

public class Neuron {
    private Vector<Double> weights;
    private Vector<Neuron> parents;
    private double value = 0;
    private int layerIndex = 0;
    private int index = 0;
    private int activationIndex = 0;
    private boolean knownValue = false;
    public final int IDENTITY = 0;
    public final int TANH = 1;

    public Neuron(int ind, int actInd)
    {
        index = ind;
        activationIndex = actInd;
        weights = new Vector<Double>();
        parents = new Vector<Neuron>();
    }

    public Neuron(int ind)
    {
        index = ind;
        activationIndex = 0;
        weights = new Vector<Double>();
        parents = new Vector<Neuron>();
    }

    public void addParent(Neuron neuron, double weight){
        parents.add(neuron);
        weights.add(weight);
    }
    public void removeParent(Neuron neuron){
        int n = neuron.getIndex();
        int target = -1;
        for(int i = 0; i < parents.size(); i++){
            if(parents.get(i).getIndex() == n){
                target = i;
            }
        }
        if(target != -1){
            parents.remove(target);
            weights.remove(target);
        }
        else{
            System.out.println("No such parent found.");
        }
    }

    public Vector<Neuron> getParents(){
        return parents;
    }

    public void setValue(double x){
        value = x;
        knownValue = true;
    }

    public double getValue(){
        if(knownValue == false){
            this.computeValue();
            knownValue = true;
        }
        return value;
    }
    private void computeValue(){
        double sum = 0;
        for(int i = 0; i < parents.size(); i++){
            sum += parents.get(i).getValue()*weights.get(i);
        }

        if(activationIndex == IDENTITY) {
            value = sum;
        }
        else if(activationIndex == TANH) {
            value = Math.tanh(sum);
        }
    }
    public int getIndex(){
        return index;
    }

}
