package matrix;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class matrixCalculatorTest {
	
	@Test
	void testMatrixGenerating() {
		
		matrixGenerator generator = new matrixGenerator();
		
		@SuppressWarnings("unused")
		float[][] matrix = generator.generateMatrix(5, 4, 2.4f, 2.5f, 5);
		
//		for (float[] fs : matrix) {
//			for (float element : fs) {
//				System.out.print(element + " ");
//			}
//			System.out.println();
//		}
	}
	

    @Test
    void testMatrixAdditionSuccess() {
        // 1. Arrange: Instantiate the calculator and set up 2x2 matrices
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };
        float[][] m2 = {
            {5.0f, 6.0f},
            {7.0f, 8.0f}
        };
        float[][] expected = {
            {6.0f, 8.0f},
            {10.0f, 12.0f}
        };

        // 2. Act: Call the main public method with '+'
        float[][] result = calculator.Matrix("+", m1, m2);

        // 3. Assert: Check if the arrays are perfectly equal
        assertArrayEquals(expected, result, "The matrix addition did not return the expected values.");
    }
    
    @Test
    void testMatrixSubstractionSuccess() {
        // 1. Arrange: Instantiate the calculator and set up 2x2 matrices
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };
        float[][] m2 = {
            {5.0f, 6.0f},
            {7.0f, 8.0f}
        };
        float[][] expected = {
            {-4.0f, -4.0f},
            {-4.0f, -4.0f}
        };

        // 2. Act: Call the main public method with '+'
        float[][] result = calculator.Matrix("-", m1, m2);

        // 3. Assert: Check if the arrays are perfectly equal
        assertArrayEquals(expected, result, "The matrix addition did not return the expected values.");
    }
    
    @Test
    void testMatrixMultiplicationSuccess() {
        // 1. Arrange: Instantiate the calculator and set up 2x2 matrices
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };
        float[][] m2 = {
            {1.0f, 0.0f},
            {0.0f, 1.0f}
        };
        float[][] expected = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };

        // 2. Act: Call the main public method with '+'
        float[][] result = calculator.Matrix("*", m1, m2);

        // 3. Assert: Check if the arrays are perfectly equal
        assertArrayEquals(expected, result, "The matrix addition did not return the expected values.");
    }
    
    @Test
    void testMatrixMultiplicationDifrentSizeSuccess() {
        // 1. Arrange: Instantiate the calculator and set up 2x2 matrices
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };
        float[][] m2 = {
            {1.0f},
            {2.0f}
        };
        float[][] expected = {
            {5.0f},
            {11.0f}
        };

        // 2. Act: Call the main public method with '+'
        float[][] result = calculator.Matrix("*", m1, m2);

        // 3. Assert: Check if the arrays are perfectly equal
        assertArrayEquals(expected, result, "The matrix addition did not return the expected values.");
        
//        for (float[] resultRow : result) {
//			for (float value : resultRow) {
//				System.out.print(value);
//			}
//			System.out.println();
//		}
    }
    
    @Test
    void testMatrixTransponingSuccess() {
        // 1. Arrange: Instantiate the calculator and set up 2x2 matrices
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };

        float[][] expected = {
            {1.0f, 3.0f},
            {2.0f, 4.0f}
        };

        // 2. Act: Call the main public method with '+'
        float[][] result = calculator.Matrix("transpone", m1);

        // 3. Assert: Check if the arrays are perfectly equal
        assertArrayEquals(expected, result, "The matrix addition did not return the expected values.");
        
//        for (float[] resultRow : result) {
//			for (float value : resultRow) {
//				System.out.print(value);
//			}
//			System.out.println();
//		}
    }
    
    @Test
    void testMatrixTransponingDifrenSizeSuccess() {
        // 1. Arrange: Instantiate the calculator and set up 2x2 matrices
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f,},
            {3.0f,}
        };

        float[][] expected = {
            {1.0f, 3.0f}
        };

        // 2. Act: Call the main public method with '+'
        float[][] result = calculator.Matrix("transpone", m1);

        // 3. Assert: Check if the arrays are perfectly equal
        assertArrayEquals(expected, result, "The matrix addition did not return the expected values.");
        
//        for (float[] resultRow : result) {
//			for (float value : resultRow) {
//				System.out.print(value);
//			}
//			System.out.println();
//		}
    }

    @Test
    void testMatrixDeterminant2x2Success() {
        // 1. Arrange: Instantiate the calculator and set up a 2x2 matrix
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {3.0f, 8.0f},
            {4.0f, 6.0f}
        };
        // Expected value: (3 * 6) - (8 * 4) = 18 - 32 = -14.0f
        // To keep assertArrayEquals happy with your method's structure, we can pack it into a 1x1 array
        float[][] expected = {
            {-14.0f}
        };

        // 2. Act: Call the main public method with 'd' for determinant
        float[][] result = calculator.Matrix("determinant", m1);

        // 3. Assert: Check if the single element array matches
        assertArrayEquals(expected, result, "The 2x2 matrix determinant did not return the expected value.");
    }

    @Test
    void testMatrixDeterminant3x3Success() {
        // 1. Arrange: Instantiate the calculator and set up a 3x3 matrix
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {6.0f, 1.0f, 1.0f},
            {4.0f, -2.0f, 5.0f},
            {2.0f, 8.0f, 7.0f}
        };
        // Expected value based on Laplace expansion example = -306.0f
        float[][] expected = {
            {-306.0f}
        };

        // 2. Act: Call the main public method with 'd'
        float[][] result = calculator.Matrix("determinant", m1);

        // 3. Assert: Check if the single element array matches
        assertArrayEquals(expected, result, "The 3x3 matrix determinant did not return the expected value.");
    }

    @Test
    void testMatrixMultiplicationIncompatibleSizes() {
        // 1. Arrange: Instantiate the calculator with mismatched inner dimensions
        matrixCalculator calculator = new matrixCalculator();
        
        // m1 is 2x3, m2 is 2x2 (3 columns vs 2 rows -> Invalid)
        float[][] m1 = {
            {1.0f, 2.0f, 3.0f},
            {4.0f, 5.0f, 6.0f}
        };
        float[][] m2 = {
            {7.0f, 8.0f},
            {9.0f, 10.0f}
        };

        // 2. Act: Attempt multiplication
        float[][] result = calculator.Matrix("*", m1, m2);

        // 3. Assert: It should drop to the fall-through empty array
        assertArrayEquals(new float[0][0], result, "Incompatible multiplication sizes should return the empty placeholder array.");
    }

    @Test
    void testMatrixDeterminantNonSquareMatrix() {
        // 1. Arrange: Create a non-square 2x3 matrix
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f, 3.0f},
            {4.0f, 5.0f, 6.0f}
        };

        // 2. Act: Attempt determinant calculation
        float[][] result = calculator.Matrix("determinant", m1);

        // 3. Assert: Since it fails the square requirement, it should fall through to the empty placeholder array
        assertArrayEquals(new float[0][0], result, "Determinant of a non-square matrix should drop to the empty placeholder array.");
    }
    
    @Test
    void testMatrixAdditionIncompatibleSizes() {
        matrixCalculator calculator = new matrixCalculator();
        
        // m1 is 2x2, m2 is 3x2 (different rows)
        float[][] m1 = { {1.0f, 2.0f}, {3.0f, 4.0f} };
        float[][] m2 = { {5.0f, 6.0f}, {7.0f, 8.0f}, {9.0f, 10.0f} };

        // Act: Run it. Right now your code falls through to the placeholder return
        float[][] result = calculator.Matrix("+", m1, m2);

        // Assert: It should return the placeholder float[0][0] since rows don't match
        assertArrayEquals(new float[0][0], result, "Incompatible sizes should fall through to the empty placeholder array.");
    }
    
    @Test
    void testMatrixAdjugateSuccess() {
        // 1. Arrange: Set up a 2x2 matrix
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };
        // The adjugate of [[a, b], [c, d]] is [[d, -b], [-c, a]]
        float[][] expected = {
            {4.0f, -2.0f},
            {-3.0f, 1.0f}
        };

        // 2. Act: Call the main public method with "adjugate"
        float[][] result = calculator.Matrix("adjugate", m1);

        // 3. Assert: Verify the arrays match
        assertArrayEquals(expected, result, "The adjugate matrix did not return the expected values.");
    }

    @Test
    void testMatrixInverseSuccess() {
        // 1. Arrange: Set up an invertible 2x2 matrix
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {3.0f, 4.0f}
        };
        // Inverse calculation: 1/(-2) * [[4, -2], [-3, 1]]
        float[][] expected = {
            {-2.0f, 1.0f},
            {1.5f, -0.5f}
        };

        // 2. Act: Call the main public method with "inverse"
        float[][] result = calculator.Matrix("inverse", m1);

        // 3. Assert: Verify the calculated inverse is correct
        assertArrayEquals(expected, result, "The inverse matrix did not return the expected values.");
    }

    @Test
    void testMatrixInverseSingularMatrix() {
        // 1. Arrange: Set up a matrix with a determinant of 0 (singular matrix)
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f},
            {2.0f, 4.0f} // Determinant = (1*4) - (2*2) = 0
        };

        // 2. Act: Try to invert it
        float[][] result = calculator.Matrix("inverse", m1);

        // 3. Assert: It should safe-fail and return your fallback placeholder array
        assertArrayEquals(new float[0][0], result, "Singular matrices should fall through to the empty placeholder array.");
    }

    @Test
    void testMatrixInverseNonSquare() {
        // 1. Arrange: Set up a non-square matrix (2x3)
        matrixCalculator calculator = new matrixCalculator();
        
        float[][] m1 = {
            {1.0f, 2.0f, 3.0f},
            {4.0f, 5.0f, 6.0f}
        };

        // 2. Act: Try to invert it
        float[][] result = calculator.Matrix("inverse", m1);

        // 3. Assert: Non-square matrices cannot be inverted, must return fallback
        assertArrayEquals(new float[0][0], result, "Inverting a non-square matrix must drop to the empty placeholder array.");
    }
}