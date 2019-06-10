package pl.qbsapps;

public class Calculator {
    private static final String REGEX = "[-+*/^]";

    public double calculate(String operation) {
        operation = operation.replaceAll("\\s+", "");

        while (operation.contains("(")) {
            operation = removeMiddleBrackets(operation);
        }

        return RPN.compute(operation);
    }

    private String removeMiddleBrackets(String operation) {
        String insideBracketsResult = operation.substring(operation.lastIndexOf("(") + 1, operation.indexOf(")"));
        String[] numbers;
        double result = 0;

        char operand = findOperand(insideBracketsResult);
        numbers = insideBracketsResult.split(REGEX);

        switch (operand) {
            case '+':
                result = add(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
                break;
            case '-':
                result = subtract(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
                break;
            case '*':
                result = multiply(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
                break;
            case '/':
                result = divide(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
                break;
            case '^':
                result = raiseToPower(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
                break;
        }

        operation = operation.replace(operation.substring(operation.lastIndexOf("("), operation.indexOf(")") + 1), String.valueOf(result));

        return operation;
    }

    private double add(double firstNumber, double secondNumber) {
        return firstNumber + secondNumber;
    }

    private double subtract(double firstNumber, double secondNumber) {
        return firstNumber - secondNumber;
    }

    private double multiply(double firstNumber, double secondNumber) {
        return firstNumber * secondNumber;
    }

    private double divide(double firstNumber, double secondNumber) {
        return firstNumber / secondNumber;
    }

    private double raiseToPower(double firstNumber, double secondNumber) {
        return Math.pow(firstNumber, secondNumber);
    }

    private char findOperand(String operation) {
        if (operation.contains("+")) {
            return '+';
        } else if (operation.contains("-")) {
            return '-';
        } else if (operation.contains("*")) {
            return '*';
        } else if (operation.contains("/")) {
            return '/';
        } else if (operation.contains("^")) {
            return '^';
        }

        return 'e';
    }
}
