package logicalOperations;

import java.util.Random;
import java.util.ResourceBundle;

import localization.Localization;
import messageHandler.MessageType;

public class LogicGenerator {
	
	/**
	 * 
	 * @Param: Number of operations only <b>Binary Operations</b>
	 * @Param: Number of variables like <b>A, or B</b>
	 * @Param: Number of negations only <b>Unary Operations</b>
	 * @Param: Number of parenthesis only <b>amout of ()</b>
	 */
	
	
	private int usedNegations = 0;
	private int usedParentheses = 0;
	private int currentlyOpen = 0;
	private int operatorsSinceOpen = 0;
	private Random rand = new Random();	
	
	public String generateLogic(int numberOfOperations, int variables, int amoutOfNegations, int amoutOfParanthesis) {
		
		ResourceBundle lang = Localization.returnBundle();
		
		if (variables > numberOfOperations + 1) {
		    messageHandler.MessageHandler.showMessage(lang.getString("message.error.variable_overflow"), MessageType.Error);
		    return "";
		}

		if (variables > 52) {
		    messageHandler.MessageHandler.showMessage(lang.getString("message.error.variable_limit"), MessageType.Error);
		    return "";
		}

		if (variables < 1) {
		    messageHandler.MessageHandler.showMessage(lang.getString("message.error.variables_underflow"), MessageType.Error);
		    return "";
		}

		if (numberOfOperations < 0) {
		    messageHandler.MessageHandler.showMessage(lang.getString("message.error.operations_underflow"), MessageType.Error);
		    return "";
		}

		if (amoutOfNegations < 0) {
		    messageHandler.MessageHandler.showMessage(lang.getString("message.error.negations_underflow"), MessageType.Error);
		    return "";
		}

		if (amoutOfParanthesis < 0) {
		    messageHandler.MessageHandler.showMessage(lang.getString("message.error.parenthesis_underflow"), MessageType.Error);
		    return "";
		}
		
		usedParentheses = 0;
		currentlyOpen = 0;
		operatorsSinceOpen = 0;
		String logic = "";
		
		char[] variablesArray = new char[variables];
		boolean[] usedVariables = new boolean[variables];
		
		int uniqueUsedCount = 1;
		usedParentheses = 0;
		currentlyOpen = 0;
		
		enum Operations {
		    Or(" ∨ "),
		    Aand(" ∧ "),
		    Implication(" → "),
		    Equivalence(" ↔ ");

		    public final String symbol;

		    // Constructor for the enum values
		    Operations(String symbol) {
		        this.symbol = symbol;
		    }
		}
		
		int asciiStart = 65;

		for(int i = 0; i < variables; i++) {
			if(i == 26)
				asciiStart += 6;
			
			variablesArray[i] = (char) ( asciiStart + i );
		}
		
		int randomFirstVariable = rand.nextInt(variablesArray.length);
		usedVariables[randomFirstVariable] = true;
		
		logic += openParenthesisChance(amoutOfParanthesis, numberOfOperations, 0);
		logic += chanceForNegation(amoutOfNegations, numberOfOperations, 0);
		logic += variablesArray[randomFirstVariable];
		logic += closeParenthesisChance(numberOfOperations, 0) + " "; 
		
		for(int i = 0; i < numberOfOperations; i++) {
			Operations randomOp = Operations.values()[rand.nextInt(Operations.values().length)];
			int chosenVariableIndex;
			int processed = i + 1;
			
			logic += randomOp.symbol;
			operatorsSinceOpen++;
			
			if(uniqueUsedCount < variables) {
				while(true) {
					int randomSlot = rand.nextInt(usedVariables.length);
					
					if(usedVariables[randomSlot] == false) {
						usedVariables[randomSlot] = true;
						uniqueUsedCount++;
						chosenVariableIndex = randomSlot;
						break;
					}
				}
			} else {
				chosenVariableIndex = rand.nextInt(variablesArray.length);
			}
			
			logic += openParenthesisChance(amoutOfParanthesis, numberOfOperations, processed);
			logic += chanceForNegation(amoutOfNegations, numberOfOperations, i);
			logic += variablesArray[chosenVariableIndex];
			logic += closeParenthesisChance(numberOfOperations, processed) + " ";
		}
		
		return logic.trim();
	}
	
	private String chanceForNegation(int amoutOfNegations, int numberOfOperations, int variablesProcessed) {
		if(numberOfOperations - variablesProcessed == 0)
			return "";
		
		if(usedNegations != amoutOfNegations) {
			float treshold = (float) (amoutOfNegations - usedNegations) / (numberOfOperations - variablesProcessed);
			
			if(rand.nextFloat() < treshold) {
				usedNegations++;
				return "!";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	private String openParenthesisChance(int amoutOfParanthesis, int numberOfOperations, int variablesProcessed) {
	    int totalVariableSlots = numberOfOperations + 1;
	    int remainingSlots = totalVariableSlots - variablesProcessed;
	    
	    String parentheses = "";

	    while (usedParentheses < amoutOfParanthesis && remainingSlots > 0) {
	        float threshold = (float) (amoutOfParanthesis - usedParentheses) / remainingSlots;
	        
	        if (rand.nextFloat() < threshold) {
	            usedParentheses++;
	            currentlyOpen++;
	            operatorsSinceOpen = 0;
	            parentheses += "(";
	        } else {
	            break;
	        }
	    }
	    return parentheses;
	}
	
	private String closeParenthesisChance(int numberOfOperations, int variablesProcessed) {
	    int totalVariableSlots = numberOfOperations + 1;
	    int remainingSlots = totalVariableSlots - variablesProcessed;

	    if (currentlyOpen > 0) {
	        if (remainingSlots <= 1) {
	            String closures = "";
	            while (currentlyOpen > 0) {
	                closures += ")";
	                currentlyOpen--;
	            }
	            return closures;
	        }

	        if (operatorsSinceOpen > 0) {
	            float threshold = (float) currentlyOpen / remainingSlots;
	            if (rand.nextFloat() < threshold) {
	                currentlyOpen--;
	                return ")";
	            }
	        }
	    }
	    return "";
	}
}