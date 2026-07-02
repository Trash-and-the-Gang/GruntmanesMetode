package logicalOperations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;

class LogicCalculatorTest {
	
	@BeforeAll
    static void initJavaFX() {
        // This force-starts the JavaFX background thread safely
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Already initialized, we can safely ignore this
        }
    }

	@Test
	void test() {
	    LogicalCalculator calculator = new LogicalCalculator();

	    // Act
	    String[][] result = calculator.LogicCalculator("(a");
	    
	    // Guard against the NullPointerException
	    if (result == null) {
	        System.out.println("Validation passed: Result is null (popup triggered successfully)!");
	    } else {
	        for (String[] strings : result) {
	            for (String string : strings) {
	                System.out.print(string + " ");
	            }
	            System.out.println();
	        }
	    }
	    
	    try {
	        Thread.sleep(10000); // Keeps the test thread alive for 10 seconds to show the popup!
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
    
    
//    @Test
//    void testGeneration() {
//    	LogicGenerator generator = new LogicGenerator();
//    	
//    	String result = generator.generateLogic(4, 30, 7, 20);
//    	
//    	System.out.println(result);
//    	
//    	try {
//    	    Thread.sleep(10000); // Keeps the window open for 10 seconds
//    	} catch (InterruptedException e) {
//    	    e.printStackTrace();
//    	}
    	
//    	LogicalCalculator calculator = new LogicalCalculator();
//
//    	String[][] resultCalc = calculator.LogicCalculator(result);
//      
//    	for (String[] strings : resultCalc) {
//    		for (String string : strings) {
//				System.out.print(string + " ");
//			}
//			System.out.println();
//		}
//    }
}