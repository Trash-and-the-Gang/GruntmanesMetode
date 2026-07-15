package graphGenearator;

import java.util.Random;
import java.util.ResourceBundle;

import localization.Localization;
import messageHandler.MessageType;

public class AdjacentMatrixGenearator {
    
    /**
     * @param size of the adjacentmatrix
     * @param bidirectional if true then it will be a graph without orienatation and if false it can have oriantation
     * @param loops if true can have loops else the matrix wont have any loops
     * @return an int array of 1's and 0's
     */
    public int[][] generateAdjacentMatrix(int size, boolean bidirectional, boolean loops) {
    	ResourceBundle lan = Localization.returnBundle();
    	
        if(size < 1) {
        	messageHandler.MessageHandler.showMessage(lan.getString("message.error.invalid_size_for_adjacent_matrix_generation"), MessageType.Error);
			throw new IllegalArgumentException("Matrix cannot be empty.");

        }
    	
    	int[][] result = new int[size][size];
        Random rand = new Random();
        int k = 0;
        
        for(int i = 0; i < size; i++) {
            for(int j = 0 + k; j < size; j++) {
                if(i == j) {
                    result[i][j] = loops ? rand.nextInt(2) : 0;
                } else {
                    result[i][j] = rand.nextInt(2);
                }
                
                if(bidirectional) {
                    result[j][i] = result[i][j];
                }
            }
            if(bidirectional) {
                k++;
            }
        }
        return result;
    }
}