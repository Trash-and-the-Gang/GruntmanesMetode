package logicalOperations;

import org.junit.jupiter.api.Test;

class LogicCalculatorTest {

//    @Test
//    void test() {
//        LogicalCalculator calculator = new LogicalCalculator();
//
//        // Act
//        String[][] result = calculator.LogicCalculator("a");
//        
//        for (String[] strings : result) {
//			for (String string : strings) {
//				System.out.print(string + " ");
//			}
//			System.out.println();
//		}
//    }
    
    
    @Test
    void testGeneration() {
    	LogicGenerator generator = new LogicGenerator();
    	
    	String result = generator.generateLogic(3, 2, 1, 2);
    	
    	System.out.println(result);
    	
    	LogicalCalculator calculator = new LogicalCalculator();

    	// Act
    	String[][] resultCalc = calculator.LogicCalculator(result);
      
    	for (String[] strings : resultCalc) {
    		for (String string : strings) {
				System.out.print(string + " ");
			}
			System.out.println();
		}
    }
}