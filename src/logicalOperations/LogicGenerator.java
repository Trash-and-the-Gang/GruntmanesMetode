package logicalOperations;

import java.util.Random;

public class LogicGenerator {
	
	private int usedNegations = 0;
	private Random rand = new Random();	
	
	public String generateLogic(int numberOfOperations, int variables, int amoutOfNegations, int amoutOfParanthesis) {
		
		if(variables > numberOfOperations + 1) {
			//TODO: error message: impossable to create because you cant have variables > number of operations + 1
		}
		
		if(variables > 52) {
			//TODO: error message: impossable to create because the maximum allowed is 52
			
		}
		
		usedNegations = 0;
		
		String logic = "";
		
		char[] variablesArray = new char[variables];
		boolean[] usedVariables = new boolean[variables];
		
		int uniqueUsedCount = 1;
		
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
		
		logic += chanceForNegation(amoutOfNegations, numberOfOperations, 0);
		logic += variablesArray[randomFirstVariable] + " ";
		
		for(int i = 0; i < numberOfOperations; i++) {
			Operations randomOp = Operations.values()[rand.nextInt(Operations.values().length)];
			int chosenVariableIndex;
			
			logic += randomOp.symbol;
			
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
			}else {
				chosenVariableIndex = rand.nextInt(variablesArray.length);
			}
			
			logic += " " + chanceForNegation(amoutOfNegations, numberOfOperations, i) + variablesArray[chosenVariableIndex] + " ";
			
		}
		
		return logic;
	}
	
	private String chanceForNegation(int amoutOfNegations, int numberOfOperations, int variablesProcessed) {
		
		if(numberOfOperations - variablesProcessed == 0)
			return " ";
		
		if(usedNegations != amoutOfNegations) {
			
			float treshold = (float) (amoutOfNegations - usedNegations) / (numberOfOperations - variablesProcessed);
			
			if(rand.nextFloat() < treshold) {
				usedNegations++;
				return "!";
			}else {
				return " ";
			}
			
		}else {
			return " ";
		}
	}
}
