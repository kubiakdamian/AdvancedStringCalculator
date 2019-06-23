package pl.qbsapps.exception;

public class DivisionByZeroException extends RuntimeException {
    public DivisionByZeroException(String message) {
        super(message);
    }

    public DivisionByZeroException() {

    }
}
