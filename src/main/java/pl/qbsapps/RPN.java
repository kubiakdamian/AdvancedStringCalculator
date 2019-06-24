package pl.qbsapps;

import pl.qbsapps.exception.DivisionByZeroException;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class RPN {
    double compute(String expr) throws
            ArithmeticException,
            EmptyStackException {

        Stack<Double> stack = new Stack<>();
        expr = expr.replaceAll("\\s+", "");

        String[] numbers = expr.split("[-+*/^]");
        String[] operands = expr.split("[\\d.]");
        operands = removeEmptyChars(operands);

        String[] combinedData = combineTwoArrays(numbers, operands);
        String[] dataConvertedToRPN = convertInfixToRPN(combinedData);
        String finalString = convertArrayToString(dataConvertedToRPN);

        for (String token : finalString.split("\\s+")) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    double divisor = stack.pop();
                    if (divisor == 0) {
                        throw new DivisionByZeroException("Division by zero");
                    }
                    stack.push(stack.pop() / divisor);
                    break;
                case "^":
                    double exponent = stack.pop();
                    stack.push(Math.pow(stack.pop(), exponent));
                    break;
                default:
                    stack.push(Double.parseDouble(token));
                    break;
            }
        }

        return stack.pop();
    }

    private String[] convertInfixToRPN(String[] infixNotation) {
        Map<String, Integer> operands = new HashMap<>();
        operands.put("/", 5);
        operands.put("*", 5);
        operands.put("+", 4);
        operands.put("-", 4);
        operands.put("^", 6);

        List<String> L = new ArrayList<>();
        Stack<String> S = new Stack<>();

        for (String token : infixNotation) {
            if (operands.containsKey(token)) {
                while (!S.empty() && operands.get(token) <= operands.get(S.peek())) {
                    L.add(S.pop());
                }
                S.push(token);
                continue;
            }
            if (isNumber(token)) {
                L.add(token);
                continue;
            }
            throw new IllegalArgumentException("Invalid input");
        }
        while (!S.isEmpty()) {
            L.add(S.pop());
        }
        String[] stockArr = new String[L.size()];
        stockArr = L.toArray(stockArr);

        return stockArr;
    }

    private static boolean isNumber(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String[] removeEmptyChars(String[] operands) {
        List<String> operandsList = new ArrayList<>();
        for (String operand : operands) {
            if (!operand.equals("")) {
                operandsList.add(operand);
            }
        }
        String[] stockArr = new String[operandsList.size()];
        stockArr = operandsList.toArray(stockArr);

        return stockArr;
    }

    private String[] combineTwoArrays(String[] numbers, String[] operands) {
        List<String> combined = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            combined.add(numbers[i]);
            if (i < operands.length) {
                combined.add(operands[i]);
            }
        }
        String[] stockArr = new String[combined.size()];
        stockArr = combined.toArray(stockArr);

        return stockArr;
    }

    private String convertArrayToString(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String element : array) {
            builder.append(element).append(" ");
        }

        return builder.toString().trim();
    }
}
