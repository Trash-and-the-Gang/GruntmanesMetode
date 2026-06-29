package logicalOperations;

import org.junit.jupiter.api.Test;

class logicCalculatorTest {

    @Test
    void test() {
        logicalCalculator calculator = new logicalCalculator();

        // Act
        String[][] result = calculator.LogicCalculator("(a & b <=> c => d (g <=>w)) =>l | !l");
        
        for (String[] strings : result) {
			for (String string : strings) {
				System.out.print(string + " ");
			}
			System.out.println();
		}
    }
}