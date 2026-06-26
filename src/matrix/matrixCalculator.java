package matrix;

public class matrixCalculator {
	
	/**
     * Performs a binary operation (Addition, Subtraction, Multiplication) on two matrices.
     *
     * @param operation The math operation to perform (+, -, *, transpone, determinant, inverse).
     * @param matrix1   The first matrix.
     * @param matrix2   The second matrix. <b>NOT NEEDED FOR SOME CALCULATIONS</b>
     * @return A new <b>2D</b> float array containing the result of the calculation.
     */
	
	public float[][] Matrix(String calculationType, float matrix1[][], float matrix2[][]) {
		
		int matrix1_row = matrix1.length;
		int matrix1_collumn = matrix1[0].length;
		
		int matrix2_row = matrix2.length;
		int matrix2_collumn = matrix2[0].length;

		
		calculationType = calculationType.toLowerCase();
		switch(calculationType) {
		case "*":
			if(matrix1_collumn == matrix2_row) {
				return multiplyMatrix(matrix1, matrix2, matrix1_row, matrix2_collumn);
			}else {
				//TODO: error message popup
			}
			break;
		
		case "+":
			if(matrix1_row == matrix2_row) {
				if(matrix1_collumn == matrix2_collumn) {
					return addMatrix(matrix1, matrix2, matrix1_row, matrix1_collumn);
				}else {
					//TODO: error message collumns dont match
				}
			}else {
				//TODO: error message rows dont match
			}
			break;
					
		case "-":
			if(matrix1_row == matrix2_row) {
				if(matrix1_collumn == matrix2_collumn) {
					return subtractMatrix(matrix1, matrix2, matrix1_row, matrix1_collumn);
				}else {
					//TODO: error message collumns dont match
				}
			}else {
				//TODO: error message rows dont match
			}
			break;
		}
		
		//Placeholder
		return new float[0][0];
	}
	
	public float[][] Matrix(String calculationType, float matrix[][]) {
		
		int matrix_row = matrix.length;
		int matrix_collumn = matrix[0].length;
		
		calculationType = calculationType.toLowerCase();
		switch(calculationType) {
		
		case "transpone":
			return transponeMatrix(matrix, matrix_row, matrix_collumn);
			
		case "inverse":
			if(matrix_row == matrix_collumn) {
				float det = determinantMatrix(matrix);
				
				if (det == 0.0f) {
					//TODO: error message popup ("Matrix is singular and cannot be inverted")
					return new float[0][0];
				}
				
				float[][] adjugate = adjugateMatrix(matrix, matrix_row, matrix_collumn);
				return divideMatrixByScalar(adjugate, det, matrix_row, matrix_collumn);
			}else {
				//TODO: error message collumns and rows dont match
			}
			break;
			
		case "determinant":
			if(matrix_row == matrix_collumn) {
				float det = determinantMatrix(matrix);
				return new float[][] { { det } };
			}else {
				//TODO: error message collumns and rows dont match
			}
			break;

		case "adjugate":
		case "adjunkt":
			if(matrix_row == matrix_collumn) {
				return adjugateMatrix(matrix, matrix_row, matrix_collumn);
			}else {
				//TODO: error message collumns and rows dont match
			}
			break;
		}
		
		//Placeholder
		return new float[0][0];
	}
	
	//
	//Calculations
	//
	
	public float[][] adjugateMatrix(float[][] matrix, int rows, int cols) {
	    
	    float[][] cofactorMatrix = new float[rows][cols];
	    
	    // 1. Build the Cofactor Matrix
	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            // Get the sub-matrix by excluding the current row and column
	            float[][] subMatrix = getSubMatrix(matrix, i, j);
	            
	            // Calculate the determinant of the sub-matrix
	            float minorDeterminant = determinantMatrix(subMatrix);
	            
	            // Determine the sign using (-1)^(i+j)
	            float sign = ((i + j) % 2 == 0) ? 1.0f : -1.0f;
	            
	            // Store the cofactor value
	            cofactorMatrix[i][j] = sign * minorDeterminant;
	        }
	    }
	    
	    // 2. The Adjugate is the transpose of the Cofactor Matrix
	    return transponeMatrix(cofactorMatrix, rows, cols);
	}
	
	public float determinantMatrix(float[][] matrix) {
	    int n = matrix.length;
	    
	    // Base Case 1: 1x1 Matrix
	    if (n == 1) {
	        return matrix[0][0];
	    }
	    
	    // Base Case 2: 2x2 Matrix (for performance optimization)
	    if (n == 2) {
	        return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
	    }
	    
	    float det = 0;
	    
	    // Recursive Case: Expand along the first row (row 0)
	    for (int j = 0; j < n; j++) {
	        // Get the sub-matrix by excluding row 0 and column j
	        float[][] subMatrix = getSubMatrix(matrix, 0, j);
	        
	        // Alternating sign: positive for even columns, negative for odd columns
	        float sign = (j % 2 == 0) ? 1.0f : -1.0f;
	        
	        // Add to the total determinant sum
	        det += sign * matrix[0][j] * determinantMatrix(subMatrix);
	    }
	    
	    return det;
	}

	// Helper method to create a smaller matrix by excluding a specific row and column
	private float[][] getSubMatrix(float[][] matrix, int excludingRow, int excludingCol) {
	    int n = matrix.length;
	    float[][] subMatrix = new float[n - 1][n - 1];
	    
	    int r = 0; // Row pointer for the new sub-matrix
	    for (int i = 0; i < n; i++) {
	        if (i == excludingRow) {
	            continue; // Skip the row we want to exclude
	        }
	        
	        int c = 0; // Column pointer for the new sub-matrix
	        for (int j = 0; j < n; j++) {
	            if (j == excludingCol) {
	                continue; // Skip the column we want to exclude
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
	
	// Helper method to handle dividing a matrix by a single scalar value (the determinant)
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