package matrix;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

class matrixCalculatorTest {

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
}