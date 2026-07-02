package matrix;

import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import localization.Localization;
import messageHandler.MessageType;

public class MatrixGenerator {
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
        
    	ResourceBundle lang = Localization.returnBundle();
    	
    	if (width < 1 || height < 1) {
            messageHandler.MessageHandler.showMessage(lang.getString("message.error.invalid_dimensions"), MessageType.Error);
            return null;
        }
    	
    	if (decimalPlaces < 0) {
            decimalPlaces = 0;
        }
        if (decimalPlaces > 7) { // Float data types lose structural accuracy past 7 digits
            messageHandler.MessageHandler.showMessage(lang.getString("message.error.decimal_limit"), MessageType.Error);
            return null;
        }
        
        float[][] matrix = new float[width][height];
        
        if (minInterval > maxInterval) {
            float temp = minInterval;
            minInterval = maxInterval;
            maxInterval = temp;
        }
        
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