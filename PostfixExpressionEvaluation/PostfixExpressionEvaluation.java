//Lena Gran 
//N03734335


package PostfixExpressionEvaluation;

import java.util.Scanner;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;

public class PostfixExpressionEvaluation {
    public static void main(String[] args) {
        System.out.println("Hello! This is a postfix form expression calculator.\n");

        String fileName = "/Users/lenagran/Desktop/AdvancedDataStructures/PostfixExpressionEvaluation/in.dat";
        Scanner inputStream = null;
        Scanner inputScanner = new Scanner(System.in);

        try {
            inputStream = new Scanner(new File(fileName));

            //While the file has more lines to read
            while (inputStream.hasNextLine()) {
                String line = inputStream.nextLine().trim();
    
                if(line.isEmpty()) {
                    continue;
                }
                //If the line contains a $, trim
                int lineToPrint = line.indexOf("$");
                if(lineToPrint != -1) {
                    line = line.substring(0, lineToPrint).trim();
                }
                System.out.println("The expression to be evaluated is " + line);

                Map<String, Integer> variableValues = new HashMap<>();
                Stack<Integer> stack = new Stack<>();
               
                Scanner expressionScanner = new Scanner(line);
                
                //While the expression has more tokens to read
                while(expressionScanner.hasNext()) {
                    String token = expressionScanner.next();

                    if(token.equals("$")) {
                        break;
                    }

                    if(token.matches("-?[0-9]+")) {
                        stack.push(Integer.parseInt(token));
                    } else if(token.matches("[a-z][a-zA-Z]*")) {
                    
                        if(!variableValues.containsKey(token)) {
                            System.out.print("Enter the value of " + token + " > ");
                            int value = inputScanner.nextInt();
                            variableValues.put(token, value);
                        } 
                        stack.push(variableValues.get(token));
                    } else {
                        int result = 0;
                        int operand1, operand2;
                        switch(token) {
                            case "+":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 + operand2;
                                stack.push(result);
                                break;
                            case "-":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 - operand2;
                                stack.push(result);
                                break;
                            case "*":   
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 * operand2;
                                stack.push(result);
                                break;  
                            case "/":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                    if (operand2 == 0) {
                                        System.out.println("Division by zero not possible");
                                        break;
                                    } else {
                                        result = operand1 / operand2;
                                        stack.push(result);
                                        break;
                                    }
                            case "_":
                                operand1 = stack.pop();
                                result = -operand1;
                                stack.push(result);
                                break;
                            case "!":
                                operand1 = stack.pop();
                                if(operand1 < 0) {
                                    System.out.println("Factorial of negative number not possible");
                                    break;
                                } else if (operand1 == 0) {
                                    stack.push(1);
                                    break;
                                } else {
                                    result = 1;
                                    for(int i = 1; i <= operand1; i++) {
                                        result *= i;
                                    }
                                    stack.push(result);
                                    break;
                                }
                            case "#":
                                operand1 = stack.pop();
                                if (operand1 < 0) {
                                    System.out.println("Square root of negative number not possible");
                                    break;
                                }
                                result = (int) Math.sqrt(operand1);
                                stack.push(result);
                                break;
                            case "^":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = (int) Math.pow(operand1, operand2);
                                stack.push(result);
                                break;
                            case "<":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 < operand2 ? 1 : 0;
                                stack.push(result);
                                break;
                            case "<=":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 <= operand2 ? 1 : 0;
                                stack.push(result);
                                break;
                            case ">":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 > operand2 ? 1 : 0;
                                stack.push(result);
                                break;
                            case ">=":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 >= operand2 ? 1 : 0;
                                stack.push(result);
                                break;
                            case "==":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 == operand2 ? 1 : 0;
                                stack.push(result);
                                break;
                            case "!=":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 != operand2 ? 1 : 0;
                                stack.push(result);
                                break;
                            case "&&":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 != 0 && operand2 != 0 ? 1 : 0;
                                stack.push(result);
                                break;
                            case "||":
                                operand2 = stack.pop();
                                operand1 = stack.pop();
                                result = operand1 != 0 || operand2 != 0 ? 1 : 0;
                                stack.push(result);
                                break;
                            default: 
                                System.out.println("Invalid token");
                        }
                    }
                }
                expressionScanner.close();

                if (!stack.isEmpty()) {
                    System.out.println("The value of this expression is " + stack.pop() + ". \n");
                } else {
                    System.out.println("Error");
                }
            }

            inputStream.close();
            System.out.println("\nBye-bye!");
                    
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        } 
        inputScanner.close();
    }
}
