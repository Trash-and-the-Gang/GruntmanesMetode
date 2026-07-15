package graphGenearator;

import org.junit.jupiter.api.Test;

class GraphGeneratorTest {

	@Test
    public void testGraphGenerator() {
		AdjacentMatrixGenearator generator = new AdjacentMatrixGenearator();
		
		int[][] result = generator.generateAdjacentMatrix(5, true, false);
		
		for (int[] is : result) {
			for (int i : is) {
				System.out.print(i + " | ");
			}
			System.out.println();
		}
	}

}
