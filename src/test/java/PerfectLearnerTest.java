import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

/**
 * Created by jedchou on 11/15/15.
 */
public class PerfectLearnerTest {

    @Test
    public void testSetParameters() throws Exception {

    }

    @Test
    public void testFindIndex() throws Exception {
        PerfectLearner brian = new PerfectLearner(1);
        TicTacToeState mystate1 = new TicTacToeState(3,3);
        mystate1.board[0][0] = 1;
        mystate1.board[0][1] = 1;
        mystate1.board[0][2] = 1;
        mystate1.board[1][1] = 1;
        assertTrue(brian.findIndex(mystate1) == 9558);

        TicTacToeState mystate2 = new TicTacToeState(3,3);
        mystate2.board[2][0] = 1;
        mystate2.board[2][1] = 2;
        mystate2.board[0][2] = 1;
        mystate2.board[1][2] = 2;
        assertTrue(brian.findIndex(mystate2) == 798);

        TicTacToeState mystate3 = new TicTacToeState(3,3);
        mystate3.board[0][0] = 1;
        mystate3.board[0][1] = 2;
        mystate3.board[0][2] = 1;
        mystate3.board[1][0] = 2;
        mystate3.board[1][1] = 1;
        mystate3.board[1][2] = 2;
        mystate3.board[2][0] = 1;
        mystate3.board[2][1] = 2;
        mystate3.board[2][2] = 1;
        assertTrue(brian.findIndex(mystate3) == 12301);
    }

    @Test
    public void testUpdateQ() throws Exception {

    }

    @Test
    public void testMaxQDiff() throws Exception {

    }

    @Test
    public void testSaveBrain() throws Exception {
        PerfectLearner brian = new PerfectLearner(1);

        brian.qValues[0][0] = 158;
        brian.qValues[8][5] = -2;
        brian.qValues[1000][4] = 0.5;

        brian.saveBrain("test");

        BufferedReader reader = new BufferedReader(new FileReader("/Users/jedchou/miscellaneous_scripts/Tictactoe/src/test/testQVal.csv"));
        //System.out.println(reader.readLine());
        assertTrue((reader.readLine()).equals("158.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0"));
        for (int i = 0; i < 7; i++){
            reader.readLine();
        }
        //System.out.println(reader.readLine());
        assertTrue((reader.readLine()).equals("0.0,0.0,0.0,0.0,0.0,-2.0,0.0,0.0,0.0"));
        for (int i = 0; i < 991; i++){
            reader.readLine();
        }
        assertTrue((reader.readLine()).equals("0.0,0.0,0.0,0.0,0.5,0.0,0.0,0.0,0.0"));
    }

    @Test
    public void testLoadBrain() throws Exception {
        PerfectLearner brian = new PerfectLearner(1);

        brian.loadBrain("/Users/jedchou/miscellaneous_scripts/Tictactoe/src/test/test");
        assertTrue(brian.qValues[0][0]==158.0);
        assertTrue(brian.qValues[8][5]==-2.0);
        assertTrue(brian.qValues[1000][4]==0.5);

    }

    @Test
    public void testGetMove() throws Exception {

    }

    @Test
    public void testNewMatch() throws Exception {

    }

    @Test
    public void testReceiveState() throws Exception {

    }

    @Test
    public void testReceiveResult() throws Exception {

    }
}