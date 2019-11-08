import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestShape {

    BufferedReader in, out;

    public TestShape() {

    }

    /* public void testFromFiles(String inFile, String outFile) throws IOException {
        try {
            in = new BufferedReader(new FileReader(inFile));
            out = new BufferedReader(new FileReader(outFile));
        } catch (FileNotFoundException f) {
            System.out.println(f);
            f.printStackTrace();
        }

        String[] cmd = in.readLine().split(" ");
        String outStr = out.readLine();

        float[] inputs = new float[cmd.length - 1];
        for (int i = 1; i < cmd.length; i++) {
            inputs[i - 1] = Float.parseFloat(cmd[i]);
        }

        switch(cmd[0]) {
            case "ADD_TRIANGLE": {
                boolean output = getBoolean(outStr);
                testAddTriangle(inputs, output);
                break;
            } case "TYPE_MESH": {
                int output = getInt(outStr);
                testTypeMesh(inputs, output);
                break;
            } case "COUNT_CNNECTED_COMPONENTS": {
                int output = getInt(outStr);
                testCountConnected(inputs, output);
                break;
            } case "BOUNDARY_EDGES": {
                BasicPoint[] output = getPointArr(outStr);
                testBoundaryEdges(inputs, output);
                break;
            } case "IS_CONNECTED": {
                boolean output = getBoolean(outStr);
                testIsConnected(inputs, output);
                break;
            } case "NEIGHBORS_OF_POINT": {
                
            }
                
        }
    } */
}