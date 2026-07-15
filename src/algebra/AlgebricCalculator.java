package algebra;

import java.util.Map;

public class AlgebricCalculator {
	
	//Possable termination for this
	
	public interface Node{
		double evaluate(Map<Character, Double> variables);
		String toString();
		
	}
	
	static class NumberNode implements Node{
		private final double value;
		
		NumberNode(double value){
			this.value = value;
			
		}
		
		@Override
		public double evaluate(Map<Character, Double> variables) {
			return value;
			
		}
		
		@Override
		public String toString() {
			return (value % 1 == 0) ? String.valueOf((int) value) : String.valueOf(value);
		}
	}
	
//	static class VariableNode implements Node{
//		private final char name;
//		
//		VariableNode(char name){
//			this.name = name;
//			
//		}
//		
//		@Override
//		public double evalueate(Map<Character, Double> variables) {
//			if(!variables.containsKey(name)) {
//				
//			}
//		}
//	}
	
	
	
}
