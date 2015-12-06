import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jedchou on 12/6/15.
 */
public class NeuronTest {

    @Test
    public void testAddParent() throws Exception {
        Neuron fred = new Neuron(5);
        Neuron marci = new Neuron(10);
        Neuron rick = new Neuron(20);
        fred.addParent(marci, 3);
        fred.addParent(rick, 2);
        assertTrue(fred.getParents().get(0).getIndex() == marci.getIndex());
        assertTrue(fred.getParents().get(1).getIndex() == rick.getIndex());
    }

    @Test
    public void testRemoveParent() throws Exception {
        Neuron fred = new Neuron(5);
        Neuron marci = new Neuron(10);
        Neuron rick = new Neuron(20);
        fred.addParent(marci, 3);
        fred.addParent(rick, 2);
        fred.removeParent(marci);
        assertTrue(fred.getParents().get(0).getIndex() == rick.getIndex());
    }

    @Test
    public void testGetValue() throws Exception {
        Neuron fred = new Neuron(5);
        Neuron marci = new Neuron(10);
        marci.setValue(1.0);
        Neuron rick = new Neuron(20);
        rick.setValue(2.0);
        fred.addParent(marci, 3);
        fred.addParent(rick, 2);
        assertTrue(fred.getValue() == 1.0*3 + 2.0*2);
    }

    @Test
    public void testGetIndex() throws Exception {
        Neuron fred = new Neuron(5);
        Neuron marci = new Neuron(10);
        Neuron rick = new Neuron(20);
        Neuron lori = new Neuron(25);
        fred.addParent(marci, 3);
        fred.addParent(rick, 2);
        fred.addParent(lori, 4);
        assertTrue(fred.getParents().get(1).getIndex() == 20);
    }
}