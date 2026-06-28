package matrix;

import java.util.concurrent.ThreadLocalRandom;

public class matrixGenerator {
	/**
     * Performs matrix generation
     *
     * @param number of collumns
     * @param number of rows
     * @param minimal interval for generating numbers(float)
     * @param maximum interval for generating numbers(float)
     * @param amout of decimal places
     * @return A new <b>2D</b> float array containing the result requested matrix.
     */
    public float[][] generateMatrix(int width, int height, float minInterval, float maxInterval, int decimalPlaces) {
        
        float[][] matrix = new float[width][height];
        
        if (minInterval > maxInterval) {
            float temp = minInterval;
            minInterval = maxInterval;
            maxInterval = temp;
        }
        
        if(decimalPlaces < 0)
        	decimalPlaces = 0;
        
        float scale = (float) Math.pow(10, decimalPlaces);
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float randomFloat = ThreadLocalRandom.current().nextFloat(minInterval, maxInterval);
                
                matrix[i][j] = Math.round(randomFloat * scale) / scale;
            }
        }
        
        return matrix;
    }
}