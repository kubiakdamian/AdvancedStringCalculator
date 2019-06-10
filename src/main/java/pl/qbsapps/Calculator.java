package pl.qbsapps;

public class Calculator {
    private static final String REGEX = "[-+*/^]";

    public double calculate(String operation) {
        operation = operation.replaceAll("\\s+", "");

        String[] numbers;

        char operand = findOperand(operation);
        numbers = operation.split(REGEX);

        switch (operand) {
            case '+':
                return add(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
            case '-':
                return subtract(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
            case '*':
                return multiply(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
            case '/':
                return divide(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
            case '^':
                return raiseToPower(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]));
        }

        return 0;
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
