package logicalOperations;

import java.util.*;

public class LogicalCalculator {

    // --- 1. TREE NODE DEFINITIONS ---
    interface Node {
        boolean evaluate(Map<Character, Boolean> assignments);
        String toString();
    }

    static class VariableNode implements Node {
        char name;
        VariableNode(char name) { this.name = name; }
        public boolean evaluate(Map<Character, Boolean> assignments) { return assignments.get(name); }
        public String toString() { return String.valueOf(name); }
    }

    static class NotNode implements Node {
        Node child;
        NotNode(Node child) { this.child = child; }
        public boolean evaluate(Map<Character, Boolean> assignments) { return !child.evaluate(assignments); }
        public String toString() { return "!" + child.toString(); }
    }

    static class BinaryOpNode implements Node {
        char op;
        Node left, right;
        BinaryOpNode(char op, Node left, Node right) { this.op = op; this.left = left; this.right = right; }
        public boolean evaluate(Map<Character, Boolean> assignments) {
            boolean l = left.evaluate(assignments);
            boolean r = right.evaluate(assignments);
            switch (op) {
                case '&': return l && r;
                case '|': return l || r;
                case '>': return !l || r;      // implication: A → B
                case '=': return l == r;       // biconditional: A ↔ B
                default: throw new IllegalStateException("Unknown operator: " + op);
            }
        }
        public String toString() {
            String symbol;
            switch (op) {
                case '&': symbol = "∧"; break;
                case '|': symbol = "∨"; break;
                case '>': symbol = "→"; break;
                case '=': symbol = "↔"; break;
                default: symbol = String.valueOf(op);
            }
            return "(" + left.toString() + " " + symbol + " " + right.toString() + ")";
        }
    }

    // --- 2. MAIN CALCULATOR METHOD ---
    public String[][] LogicCalculator(String fullLogic) {
    	
    	if(fullLogic == "") {
    		return null;
    	}
    	
        String expression = normalizeOperators(fullLogic.replaceAll("\\s+", ""));
        
        // Find base variables
        List<Character> variables = getUniqueVariables(expression);
        Collections.sort(variables);

        // Build the AST Root
        Node root = buildAST(expression);

        // Gather all sub-expressions by traversing the tree
        List<Node> subExpressions = new ArrayList<>();
        gatherSubExpressions(root, subExpressions);
        
        // Remove base variables from subExpressions to avoid duplicates in the header
        subExpressions.removeIf(node -> node instanceof VariableNode);
        
        // Final columns: base variables followed by sub-calculations
        List<Node> allColumns = new ArrayList<>();
        for (char v : variables) allColumns.add(new VariableNode(v));
        allColumns.addAll(subExpressions);

        int numRows = (int) Math.pow(2, variables.size());
        String[][] truthTable = new String[numRows + 1][allColumns.size()];

        // Fill Header Row
        for (int j = 0; j < allColumns.size(); j++) {
            truthTable[0][j] = allColumns.get(j).toString();
        }

        // Fill Data Rows
        for (int i = 0; i < numRows; i++) {
            Map<Character, Boolean> assignments = new HashMap<>();
            for (int j = 0; j < variables.size(); j++) {
                boolean value = ((i >> (variables.size() - 1 - j)) & 1) == 1;
                assignments.put(variables.get(j), value);
            }

            for (int j = 0; j < allColumns.size(); j++) {
                boolean result = allColumns.get(j).evaluate(assignments);
                truthTable[i + 1][j] = result ? "1" : "0";
            }
        }

        return truthTable;
    }

    // --- 3. PARSING & HELPER METHODS ---
    private Node buildAST(String expression) {
        String postfix = infixToPostfix(expression);
        Stack<Node> stack = new Stack<>();

        for (char ch : postfix.toCharArray()) {
            if (Character.isLetter(ch)) {
                stack.push(new VariableNode(ch));
            } else if (ch == '!') {
                stack.push(new NotNode(stack.pop()));
            } else if (ch == '&' || ch == '|' || ch == '>' || ch == '=') {
                Node right = stack.pop();
                Node left = stack.pop();
                stack.push(new BinaryOpNode(ch, left, right));
            }
        }
        return stack.pop();
    }

    private void gatherSubExpressions(Node node, List<Node> list) {
        if (node == null) return;
        if (node instanceof NotNode) {
            gatherSubExpressions(((NotNode) node).child, list);
        } else if (node instanceof BinaryOpNode) {
            gatherSubExpressions(((BinaryOpNode) node).left, list);
            gatherSubExpressions(((BinaryOpNode) node).right, list);
        }
        // Unique tracking based on string representation
        if (list.stream().noneMatch(n -> n.toString().equals(node.toString()))) {
            list.add(node);
        }
    }

    private List<Character> getUniqueVariables(String expr) {
        Set<Character> vars = new HashSet<>();
        for (char ch : expr.toCharArray()) {
            if (Character.isLetter(ch)) vars.add(ch);
        }
        return new ArrayList<>(vars);
    }

    // Maps your formal logic symbols down to single internal tokens 
    // so the rest of the stack-based math parser can evaluate them seamlessly.
    private String normalizeOperators(String expr) {
        return expr.replace("↔", "=")
                   .replace("→", ">")
                   .replace("∧", "&")
                   .replace("∨", "|");
    }

    private String infixToPostfix(String infix) {
        // Standard logic-operator precedence, highest to lowest:
        // NOT (!) > AND (&) > OR (|) > IMPLIES (→, internal '>') > IFF (↔, internal '=')
        Map<Character, Integer> precedence = Map.of('!', 5, '&', 4, '|', 3, '>', 2, '=', 1);
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char ch : infix.toCharArray()) {
            if (Character.isLetter(ch)) {
                output.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') output.append(stack.pop());
                stack.pop();
            } else if (precedence.containsKey(ch)) {
                // '!' is right-associative (unary prefix), so equal-precedence '!' on the
                // stack must NOT be popped by another incoming '!' — only strictly higher
                // precedence operators get popped in that case. '&' and '|' stay left-associative.
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       (precedence.get(stack.peek()) > precedence.get(ch) ||
                        (precedence.get(stack.peek()) == precedence.get(ch) && ch != '!'))) {
                    output.append(stack.pop());
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) output.append(stack.pop());
        return output.toString();
    }
}