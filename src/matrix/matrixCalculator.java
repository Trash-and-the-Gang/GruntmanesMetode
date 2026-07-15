package matrix;

import java.util.ResourceBundle;
import localization.Localization;
import messageHandler.MessageType;

public class MatrixCalculator {
	
    /**
     * Performs a binary operation (Addition, Subtraction, Multiplication) on two matrices.
     *
     * @param calculationType The math operation to perform (+, -, *).
     * @param matrix1         The first matrix.
     * @param matrix2         The second matrix.
     * @return A new <b>2D</b> float array containing the result of the calculation, or <b>null</b> on failure.
     */
    public float[][] Matrix(String calculationType, float matrix1[][], float matrix2[][]) {
        ResourceBundle lang = Localization.returnBundle();
		
        int matrix1_row = matrix1.length;
        int matrix1_collumn = matrix1[0].length;
		
        int matrix2_row = matrix2.length;
        int matrix2_collumn = matrix2[0].length;

        calculationType = calculationType.toLowerCase();
        switch(calculationType) {
            case "*":
                if (matrix1_collumn == matrix2_row) {
                    return multiplyMatrix(matrix1, matrix2, matrix1_row, matrix2_collumn);
                } else {
                    messageHandler.MessageHandler.showMessage(lang.getString("message.error.multiplication_mismatch"), MessageType.Error);
                    return null;
                }
            case "+":
                if (matrix1_row == matrix2_row && matrix1_collumn == matrix2_collumn) {
                    return addMatrix(matrix1, matrix2, matrix1_row, matrix1_collumn);
                } else {
                    messageHandler.MessageHandler.showMessage(lang.getString("message.error.dimensions_mismatch"), MessageType.Error);
                    return null;
                }
            case "-":
                if (matrix1_row == matrix2_row && matrix1_collumn == matrix2_collumn) {
                    return subtractMatrix(matrix1, matrix2, matrix1_row, matrix1_collumn);
                } else {
                    messageHandler.MessageHandler.showMessage(lang.getString("message.error.dimensions_mismatch"), MessageType.Error);
                    return null;
                }
        }
		
        return null;
    }
	
    /**
     * Performs a unary operation (Transpose, Inverse, Determinant, Adjugate) on a single matrix.
     *
     * @param calculationType The math operation to perform.
     * @param matrix          The target matrix.
     * @return A new <b>2D</b> float array containing the result of the calculation, or <b>null</b> on failure.
     */
    public float[][] Matrix(String calculationType, float matrix[][]) {
        ResourceBundle lang = Localization.returnBundle();
		
        int matrix_row = matrix.length;
        int matrix_collumn = matrix[0].length;
		
        calculationType = calculationType.toLowerCase();
        switch(calculationType) {
		
            case "transpone":
                return transponeMatrix(matrix, matrix_row, matrix_collumn);
			
            case "inverse":
                if (matrix_row != matrix_collumn) {
                    messageHandler.MessageHandler.showMessage(lang.getString("message.error.not_square"), MessageType.Error);
                    return null;
                }
				
                float det = determinantMatrix(matrix);
                if (det == 0.0f) {
                    messageHandler.MessageHandler.showMessage(lang.getString("message.error.singular_matrix"), MessageType.Error);
                    return null;
                }
				
                float[][] adjugate = adjugateMatrix(matrix, matrix_row, matrix_collumn);
                return divideMatrixByScalar(adjugate, det, matrix_row, matrix_collumn);
			
            case "determinant":
                if (matrix_row == matrix_collumn) {
                    float d = determinantMatrix(matrix);
                    return new float[][] { { d } };
                } else {
                    messageHandler.MessageHandler.showMessage(lang.getString("message.error.not_square"), MessageType.Error);
                    return null;
                }

            case "adjugate":
            case "adjunkt":
                if (matrix_row == matrix_collumn) {
                    return adjugateMatrix(matrix, matrix_row, matrix_collumn);
                } else {
                    messageHandler.MessageHandler.showMessage(lang.getString("message.error.not_square"), MessageType.Error);
                    return null;
                }
        }
		
        return null;
    }
	
    //Calculations options
	
    private float[][] adjugateMatrix(float[][] matrix, int rows, int cols) {
        float[][] cofactorMatrix = new float[rows][cols];
	    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float[][] subMatrix = getSubMatrix(matrix, i, j);
                float minorDeterminant = determinantMatrix(subMatrix);
                float sign = ((i + j) % 2 == 0) ? 1.0f : -1.0f;
                cofactorMatrix[i][j] = sign * minorDeterminant;
            }
        }
	    
        return transponeMatrix(cofactorMatrix, rows, cols);
    }
	
    private float determinantMatrix(float[][] matrix) {
        int n = matrix.length;
	    
        if (n == 1) {
            return matrix[0][0];
        }
	    
        if (n == 2) {
            return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        }
	    
        float det = 0;
        for (int j = 0; j < n; j++) {
            float[][] subMatrix = getSubMatrix(matrix, 0, j);
            float sign = (j % 2 == 0) ? 1.0f : -1.0f;
            det += sign * matrix[0][j] * determinantMatrix(subMatrix);
        }
	    
        return det;
    }

    private float[][] getSubMatrix(float[][] matrix, int excludingRow, int excludingCol) {
        int n = matrix.length;
        float[][] subMatrix = new float[n - 1][n - 1];
	    
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == excludingRow) {
                continue;
            }
	        
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludingCol) {
                    continue;
                }
	            
                subMatrix[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
	    
        return subMatrix;
    }
	
    private float[][] transponeMatrix(float[][] matrix, int row, int collumn) {
        float[][] resultMatrix = new float[collumn][row];
		
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < collumn; j++) {
                resultMatrix[j][i] = matrix[i][j];
            }
        }
		
        return resultMatrix;
    }

    private float[][] addMatrix(float matrix1[][], float matrix2[][], int rows, int collumns) {
        float[][] resultMatrix = new float[rows][collumns];
		
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < collumns; j++) {
                resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
		
        return resultMatrix;
    }
	
    private float[][] subtractMatrix(float matrix1[][], float matrix2[][], int rows, int collumns) {
        float[][] resultMatrix = new float[rows][collumns];
		
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < collumns; j++) {
                resultMatrix[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
		
        return resultMatrix;
    }
	
    private float[][] multiplyMatrix(float matrix1[][], float matrix2[][], int rows, int collumns) {
        float[][] resultMatrix = new float[rows][collumns];
        int innerDimension = matrix1[0].length; 
		
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < collumns; j++) { 
                float newValue = 0;
                for(int k = 0; k < innerDimension; k++) {
                    newValue += matrix1[i][k] * matrix2[k][j];
                }
                resultMatrix[i][j] = newValue;
            }
        }
		
        return resultMatrix;
    }
	
    private float[][] divideMatrixByScalar(float[][] matrix, float scalar, int rows, int cols) {
        float[][] resultMatrix = new float[rows][cols];
		
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                resultMatrix[i][j] = matrix[i][j] / scalar;
            }
        }
		
        return resultMatrix;
    }
}