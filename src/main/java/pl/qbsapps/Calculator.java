package pl.qbsapps;

import com.google.common.base.CharMatcher;
import pl.qbsapps.exception.InvalidOperationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static final String REGEX = "[-+*/^]";
    private RPN rpn = new RPN();

    public double calculate(String operation) {
        if (operation == null || operation.equals("")) {
            throw new IllegalArgumentException("Invalid input");
        }

        operation = operation.replaceAll("\\s+", "");
        checkIfTwoOperandsAppearSideBySide(operation);
        checkIfOperationContainsInvalidNumbers(operation);
        checkIfBracketsInOperationAreValid(operation);

        while (operation.contains("%")) {
            operation = removePercentage(operation);
        }
        while (operation.contains("(")) {
            operation = removeMiddleBrackets(operation);
        }

        return rpn.compute(operation);
    }

    private String removeMiddleBrackets(String operation) {
        int indexOfOpeningBracket = operation.lastIndexOf("(") + 1;
        String tempString = operation.substring(indexOfOpeningBracket);
        int indexOfClosingBracket = tempString.indexOf(")");
        String insideBracketsResult = tempString.substring(0, indexOfClosingBracket);
        String[] numbers = insideBracketsResult.split(REGEX);
        double result = 0;

        if (checkIfAllElementsAreNumbers(numbers)) {
            result = rpn.compute(insideBracketsResult);
        }
        operation = operation.replace(operation.substring(indexOfOpeningBracket - 1, indexOfClosingBracket + indexOfOpeningBracket + 1), String.valueOf(result));

        return operation;
    }

    private boolean checkIfAllElementsAreNumbers(String[] array) {
        boolean hasAllNumbers = true;

        for (String number : array) {
            if (!isNumber(number)) {
                hasAllNumbers = false;
                break;
            }
        }

        return hasAllNumbers;
    }

    private String removePercentage(String operation) {
        int indexOfPercentage = operation.indexOf("%") + 1;
        String tempString = operation.substring(0, indexOfPercentage);
        tempString = getLastNumberFromString(tempString);
        int indexOfNumber = indexOfPercentage - tempString.length();
        double convertedNumber = Double.parseDouble(tempString) / 100;
        tempString = String.valueOf(convertedNumber);
        operation = operation.replace(operation.substring(indexOfNumber - 1, indexOfPercentage), tempString);

        return operation;
    }

    private String getLastNumberFromString(String operation) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(operation);
        String result = "";
        while (m.find()) {
            result = m.group();
        }

        return result;
    }

    private void checkIfOperationContainsInvalidNumbers(String operation) {
        operation = operation.replaceAll("[()%]", "");
        String[] numbers = operation.split(REGEX);
        for (String number : numbers) {
            if (!isNumber(number)) {
                throw new InvalidOperationException("Invalid number detected: " + number);
            }
        }
    }

    private void checkIfBracketsInOperationAreValid(String operation) {
        if (countOccurrencesOfCharacter(operation, '(') > countOccurrencesOfCharacter(operation, ')')) {
            throw new InvalidOperationException("An extra left parenthesis detected");
        } else if (countOccurrencesOfCharacter(operation, '(') < countOccurrencesOfCharacter(operation, ')')) {
            throw new InvalidOperationException("An extra right parenthesis detected");
        }
    }

    private void checkIfTwoOperandsAppearSideBySide(String operation) {
        String signs;
        for (int i = 0; i < operation.length() - 2; i++) {
            signs = operation.substring(i, i + 2);
            if (signs.matches("[-+*/^]+")) {
                throw new InvalidOperationException("Missing number between " + signs.charAt(0) + " and " + signs.charAt(1) + " operators");
            }
        }
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int countOccurrencesOfCharacter(String operation, char symbol) {
        return CharMatcher.is(symbol).countIn(operation);
    }
}
