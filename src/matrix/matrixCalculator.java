package matrix;

public class matrixCalculator {
	
	
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
		}
		
		//Placeholder
		return new float[0][0];
	}
	
	
	//
	//Calculations
	//
	
	
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
				// Loop through the row of matrix1 and column of matrix2 to calculate the dot product
				for(int k = 0; k < innerDimension; k++) {
					newValue += matrix1[i][k] * matrix2[k][j];
				}
				
				resultMatrix[i][j] = newValue;
			}
		}
		
		return resultMatrix;
	}
	
}

