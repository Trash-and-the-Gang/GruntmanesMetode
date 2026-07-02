package logicalOperations;

import java.util.*;
import localization.Localization;
import messageHandler.MessageType;

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
        ResourceBundle lang = Localization.returnBundle();
        
        // 1. Guard against empty inputs
        if (fullLogic == null || fullLogic.strip().isEmpty()) {
            messageHandler.MessageHandler.showMessage(lang.getString("message.error.empty_input"), MessageType.Error);
            return null;
        }
    	
        String expression = normalizeOperators(fullLogic.replaceAll("\\s+", ""));
        
        // 2. Validate basic expression syntax rules
        if (!isExpressionSyntaxValid(expression, lang)) {
            return null; // Stop calculation immediately if a popup was shown
        }
        
        List<Character> variables = getUniqueVariables(expression);
        Collections.sort(variables);

        // 3. Build AST with tracking to guarantee no unexpected parser crashes
        Node root = buildAST(expression, lang);
        if (root == null) {
            return null; // Stop calculation immediately if a structural popup was shown
        }

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
    private boolean isExpressionSyntaxValid(String expr, ResourceBundle lang) {
        int bracketCount = 0;
        char prev = ' ';

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (ch == '(') bracketCount++;
            if (ch == ')') bracketCount--;
            
            if (bracketCount < 0) {
                messageHandler.MessageHandler.showMessage(lang.getString("error.validation.unmatched_brackets"), MessageType.Error);
                return false;
            }

            // Check for consecutive variables (e.g., AB instead of A & B)
            if (Character.isLetter(ch) && Character.isLetter(prev)) {
                messageHandler.MessageHandler.showMessage(lang.getString("error.validation.invalid_character"), MessageType.Error);
                return false;
            }
            
            // Check for duplicate binary operators (e.g., A && B)
            if ("&|>=".indexOf(ch) != -1 && "&|>=".indexOf(prev) != -1) {
                messageHandler.MessageHandler.showMessage(lang.getString("error.validation.invalid_character"), MessageType.Error);
                return false;
            }

            prev = ch;
        }

        if (bracketCount != 0) {
            messageHandler.MessageHandler.showMessage(lang.getString("error.validation.unmatched_brackets"), MessageType.Error);
            return false;
        }

        return true;
    }

    private Node buildAST(String expression, ResourceBundle lang) {
        String postfix = infixToPostfix(expression, lang);
        if (postfix == null) return null; // An error was already popped up inside conversion
        
        Stack<Node> stack = new Stack<>();

        try {
            for (char ch : postfix.toCharArray()) {
                if (Character.isLetter(ch)) {
                    stack.push(new VariableNode(ch));
                } else if (ch == '!') {
                    if (stack.isEmpty()) {
                        messageHandler.MessageHandler.showMessage(lang.getString("error.validation.invalid_character"), MessageType.Error);
                        return null;
                    }
                    stack.push(new NotNode(stack.pop()));
                } else if (ch == '&' || ch == '|' || ch == '>' || ch == '=') {
                    if (stack.size() < 2) {
                        messageHandler.MessageHandler.showMessage(lang.getString("error.validation.invalid_character"), MessageType.Error);
                        return null;
                    }
                    Node right = stack.pop();
                    Node left = stack.pop();
                    stack.push(new BinaryOpNode(ch, left, right));
                }
            }
            
            if (stack.size() != 1) {
                messageHandler.MessageHandler.showMessage(lang.getString("error.validation.invalid_character"), MessageType.Error);
                return null;
            }
            return stack.pop();
            
        } catch (Exception e) {
            messageHandler.MessageHandler.showMessage(lang.getString("error.validation.invalid_character"), MessageType.Error);
            return null;
        }
    }

    private void gatherSubExpressions(Node node, List<Node> list) {
        if (node == null) return;
        if (node instanceof NotNode) {
            gatherSubExpressions(((NotNode) node).child, list);
        } else if (node instanceof BinaryOpNode) {
            gatherSubExpressions(((BinaryOpNode) node).left, list);
            gatherSubExpressions(((BinaryOpNode) node).right, list);
        }
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

    private String normalizeOperators(String expr) {
        return expr.replace("↔", "=")
                   .replace("→", ">")
                   .replace("∧", "&")
                   .replace("∨", "|");
    }

    private String infixToPostfix(String infix, ResourceBundle lang) {
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
                if (stack.isEmpty()) {
                    messageHandler.MessageHandler.showMessage(lang.getString("error.validation.unmatched_brackets"), MessageType.Error);
                    return null;
                }
                stack.pop();
            } else if (precedence.containsKey(ch)) {
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       (precedence.get(stack.peek()) > precedence.get(ch) ||
                        (precedence.get(stack.peek()) == precedence.get(ch) && ch != '!'))) {
                    output.append(stack.pop());
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) {
            char popped = stack.pop();
            if (popped == '(' || popped == ')') {
                messageHandler.MessageHandler.showMessage(lang.getString("error.validation.unmatched_brackets"), MessageType.Error);
                return null;
            }
            output.append(popped);
        }
        return output.toString();
    }
}