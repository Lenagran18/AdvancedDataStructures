//Lena Gran 
//N03734335

package ExpressionManipulations;

import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.LinkedList;

public class ExpressionManipulation {
    public static void main(String[] args) {

        System.out.println("Hello! This is an expression manipulation calculator.\n");

        String fileName = "/Users/lenagran/Desktop/AdvancedDataStructures/ExpressionManipulations/in.dat";
        Scanner inputScanner = new Scanner(System.in);

        try (Scanner inputStream = new Scanner(new File(fileName))) {
            processExpressions(inputStream, inputScanner);
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }

        inputScanner.close();
        System.out.println("\nBye-bye!");
    }

    // Reads and processes expressions from the input stream
    private static void processExpressions(Scanner inputStream, Scanner inputScanner) {
        while (inputStream.hasNextLine()) {
            String expression = getNextExpression(inputStream);
            if (expression == null) {
                continue;
            }
            // printing the expression before it is manipulated
            System.out.println("\nThe expression to be evaluated is: " + expression);

            // showing the converted postfix form expression
            Queue<String> postfixExpression = convertToPostfix(expression);
            printPostfixExpression(postfixExpression);

            // showing the expression tree
            BTNode root = createExpressionTree(postfixExpression);
            System.out.println("\nExpression tree: ");
            printExpressionTree(root, 0);

            // printing the fully parenthesized infix form expression
            String infixExpression = fullyParenthesizedExpression(root);
            System.out.println("Fully parenthesized expression: " + infixExpression);

            // reporting the value of the expression
            int result = evaluateExpression(root);
            System.out.println("Evaluated result: " + result);

            System.out.println("\nPress <Enter> to continue...");
            inputScanner.nextLine(); // Wait for Enter key press
        }
    }

    // Get the next expression from the input stream
    private static String getNextExpression(Scanner inputStream) {
        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            int end = line.indexOf("$");
            return (end != -1) ? line.substring(0, end).trim() : line;
        }
        return null;
    }

    // Convert infix expression to postfix expression
    private static Queue<String> convertToPostfix(String expression) {
        Stack<String> stack = new Stack<>();
        Queue<String> postfixExpressionQueue = new LinkedList<>();

        Scanner expressionScanner = new Scanner(expression);
        while (expressionScanner.hasNext()) {
            String token = expressionScanner.next();
            if (token.equals(" ")) {
                continue;
            }
            if (token.equals("$")) {
                break;
            } else if (token.matches("-?[0-9]+")) {
                postfixExpressionQueue.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty()) {
                    if (stack.peek().equals("(")) {
                        stack.pop();
                        break;
                    }
                    postfixExpressionQueue.add(stack.pop());
                }
            } else if (stack.isEmpty() || stack.peek().equals("(")) {
                stack.push(token);
            } else if (precedence(token) > precedence(stack.peek())) {
                stack.push(token);
            } else {
                while (!stack.isEmpty() && precedence(token) <= precedence(stack.peek())) {
                    if (precedence(token) == precedence(stack.peek())) {
                        if (!isRightAssociative(token)) {
                            postfixExpressionQueue.add(stack.pop());
                        } else {
                            break;
                        }
                    } else {
                        postfixExpressionQueue.add(stack.pop());
                    }
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            postfixExpressionQueue.add(stack.pop());
        }
        expressionScanner.close();

        return postfixExpressionQueue;
    }

    // Print postfix expression
    private static void printPostfixExpression(Queue<String> postfixExpressionQueue) {
        System.out.print("Postfix expression: ");
        for (String token : postfixExpressionQueue) {
            System.out.print(token + " ");
        }
        System.out.println();
    }

    // Set operator precedence
    private static int precedence(String operator) {
        switch (operator) {
            case "!":
                return 5;
            case "^":
                return 4;
            case "*":
            case "/":
            case "%":
                return 3;
            case "+":
            case "-":
                return 2;
            case "<":
            case "<=":
            case ">":
            case ">=":
                return 1;
            case "==":
            case "!=":
                return 0;
            case "&&":
                return -1;
            case "||":
                return -2;
            default:
                return -3;
        }
    }

    // Check if operator is right-associative
    private static boolean isRightAssociative(String operator) {
        return operator.equals("!") || operator.equals("^");
    }

    // Create the expression tree
    public static BTNode createExpressionTree(Queue<String> postfixExpressionQueue) {
        Stack<BTNode> expressionTree = new Stack<>();

        while (!postfixExpressionQueue.isEmpty()) {
            String token = postfixExpressionQueue.poll();

            if (token.matches("-?[0-9]+")) {
                expressionTree.push(new BTNode(token));
                // Unary operator
            } else if (token.equals("!")) { 
                if (!expressionTree.isEmpty()) {
                    BTNode operand = expressionTree.pop(); // Pop the operand
                    BTNode notNode = new BTNode(token);    
                    notNode.left = operand; // Operand becomes left child of NOT
                    expressionTree.push(notNode); // Push NOT node back onto stack
                }
            } else {
                // Binary operator
                BTNode root = new BTNode(token);
                if (!expressionTree.isEmpty()) {
                    root.right = expressionTree.pop(); // Pop right operand
                }
                if (!expressionTree.isEmpty()) {
                    root.left = expressionTree.pop(); // Pop left operand
                }
                expressionTree.push(root); // Push the new tree back onto the stack
            }
            
        }
        return expressionTree.isEmpty() ? null : expressionTree.peek();
    }

    // Print the expression tree
    public static void printExpressionTree(BTNode root, int level) {
        if (root == null) {
            return;
        }

        // Print right subtree
        printExpressionTree(root.right, level + 1);

        // Print indentation
        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }
        System.out.println(root.data); // Print node value

        // Print left subtree
        printExpressionTree(root.left, level + 1);
    }

    // Check if the token is an operand
    private static boolean isOperand(String token) {
        return token.matches("-?\\d+");
    }

    // Print tree in fully parenthesized form
    public static String fullyParenthesizedExpression(BTNode root) {
        if (root == null) {
            return "";
        }

        if (isOperand(root.data)) {
            return root.data;
        }

        // Unary operator case
        if (root.data.equals("!")) {
            return root.data + " " + fullyParenthesizedExpression(root.left);
        }

        // Binary operator case
        String left = fullyParenthesizedExpression(root.left);
        String right = fullyParenthesizedExpression(root.right);

        // Return a properly formatted expression
        return "(" + left + " " + root.data + " " + right + ")";
    }

    public static int evaluateExpression(BTNode root) {
        if (root == null) {
            return 0;
        }
        if (isOperand(root.data)) {
            return Integer.parseInt(root.data);
        }

        int leftValue = evaluateExpression(root.left);
        int rightValue = evaluateExpression(root.right);

        switch (root.data) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0)
                    throw new ArithmeticException("Division by zero");
                return leftValue / rightValue;
            case "%":
                return leftValue % rightValue;
            case "^":
                return (int) Math.pow(leftValue, rightValue);
            case "!":
                return leftValue == 0 ? 1 : 0;
            case "<":
                return leftValue < rightValue ? 1 : 0;
            case "<=":
                return leftValue <= rightValue ? 1 : 0;
            case ">":
                return leftValue > rightValue ? 1 : 0;
            case ">=":
                return leftValue >= rightValue ? 1 : 0;
            case "==":
                return leftValue == rightValue ? 1 : 0;
            case "!=":
                return leftValue != rightValue ? 1 : 0;
            case "&&":
                return (leftValue != 0 && rightValue != 0) ? 1 : 0;
            case "||":
                return (leftValue != 0 || rightValue != 0) ? 1 : 0;
            default:
                throw new UnsupportedOperationException("Unknown operator: " + root.data);
        }
    }
}
