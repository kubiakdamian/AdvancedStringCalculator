import org.junit.jupiter.api.Test;
import pl.qbsapps.Calculator;
import pl.qbsapps.exception.DivisionByZeroException;
import pl.qbsapps.exception.InvalidOperationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdvancedStringCalculatorTest {

    private Calculator calculator = new Calculator();

    @Test
    void shouldRealizeBasicOperations() {
        assertThatProperResultWasReturned("1+2.5", 3.5);
        assertThatProperResultWasReturned("5^2", 25);
        assertThatProperResultWasReturned("3*7", 21);
        assertThatProperResultWasReturned("8/2", 4);
        assertThatProperResultWasReturned("2.5-5", -2.5);
    }

    @Test
    void shouldIgnoreWhiteSpaces() {
        assertThatProperResultWasReturned(" 1 +   2.5", 3.5);
        assertThatProperResultWasReturned("5^2    ", 25);
        assertThatProperResultWasReturned(" 3*7", 21);
        assertThatProperResultWasReturned("8  / 2", 4);
        assertThatProperResultWasReturned(" 2 . 5 - 5", -2.5);
    }

    @Test
    void shouldRealizeCompoundOperation() {
        assertThatProperResultWasReturned(" 1 +   2.5 * 3", 8.5);
        assertThatProperResultWasReturned(" 1 +   2.5 * 3^2", 23.5);
        assertThatProperResultWasReturned("5^2+3", 28);
        assertThatProperResultWasReturned(" 3*7*3*2", 126);
    }

    @Test
    void shouldThrowDivisionByZeroException() {
        assertThatResultThrowsDivisionByZeroException("1 / 0");
        assertThatResultThrowsDivisionByZeroException("1 / (1-1)");
        assertThatResultThrowsDivisionByZeroException("1 / ( 3 * 0)");
        assertThatResultThrowsDivisionByZeroException("1+ 1/ (2 - 2)");
    }

    private void assertThatResultThrowsDivisionByZeroException(String operation) {
        assertThrows(DivisionByZeroException.class, () -> calculator.calculate(operation));
    }

    @Test
    void shouldThrowInvalidOperationException() {
        assertThatResultThrowsInvalidOperationException("2a*3", "Invalid number detected: 2a");
        assertThatResultThrowsInvalidOperationException("((2.4-(0.4 + 1#as)) * 3)^2", "Invalid number detected: 1#as");
        assertThatResultThrowsInvalidOperationException("((2.4-(0.4 + vd1)) * 3)^2", "Invalid number detected: vd1");
    }

    private void assertThatResultThrowsInvalidOperationException(String operation, String expectedMessage) {
        InvalidOperationException invalidOperationException = assertThrows(InvalidOperationException.class, () -> calculator.calculate(operation));
        assertTrue(invalidOperationException.getMessage().contains(expectedMessage));
    }


    @Test
    void shouldRealizeOperationWithBrackets() {
        assertThatProperResultWasReturned("( 1 +   2.5) * 3^2", 31.5);
        assertThatProperResultWasReturned("((2.4-0.4) * 3)^2", 36);
        assertThatProperResultWasReturned("((2.4-(0.4 + 1)) * 3)^2", 9);
        assertThatProperResultWasReturned("(1+1)*(2+2)", 8);
        assertThatProperResultWasReturned("((2.4 - 0.4) * 3)^4", 1296);
        assertThatProperResultWasReturned("((2+2*(2+2))*(3*(2+2)))*3", 360);
    }

    @Test
    void shouldRealizeOperationWithPercentage() {
        assertThatProperResultWasReturned("2*75%", 1.5);
        assertThatProperResultWasReturned("2*150%", 3);
        assertThatProperResultWasReturned("2*10000%", 200);
        assertThatProperResultWasReturned("(1+1)*50%", 1);
        assertThatProperResultWasReturned("((2+2*(2+2))*(3*75%))*3", 67.5);
    }

    private void assertThatProperResultWasReturned(String operation, double expectedResult) {
        assertEquals(calculator.calculate(operation), expectedResult);
    }
}
