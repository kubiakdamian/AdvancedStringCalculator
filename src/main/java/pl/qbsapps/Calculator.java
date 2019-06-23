package pl.qbsapps;

public class Calculator {
    private static final String REGEX = "[-+*/^]";
    private RPN rpn = new RPN();

    public double calculate(String operation) {
        if (operation == null || operation.equals("")) {
            throw new IllegalArgumentException("Invalid input");
        }

        operation = operation.replaceAll("\\s+", "");

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

        String[] numbers;
        double result = 0;

        numbers = insideBracketsResult.split(REGEX);
        String firstNumber = numbers[0];
        String secondNumber = numbers[1];

        if (isNumber(firstNumber) && isNumber(secondNumber)) {
            result = rpn.compute(insideBracketsResult);
        }

        operation = operation.replace(operation.substring(indexOfOpeningBracket - 1, indexOfClosingBracket + indexOfOpeningBracket + 1), String.valueOf(result));

        return operation;
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
