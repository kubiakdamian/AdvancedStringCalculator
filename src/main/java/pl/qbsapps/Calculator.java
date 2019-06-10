package pl.qbsapps;

public class Calculator {
    public double calculate(String operation) {
        operation = operation.replaceAll("\\s+", "");
        return RPN.compute(operation);
    }
}
