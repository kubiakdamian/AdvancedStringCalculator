import org.junit.jupiter.api.Test;
import pl.qbsapps.Calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void shouldRealizeOperationWithBrackets() {
        assertThatProperResultWasReturned("( 1 +   2.5) * 3^2", 31.5);
        assertThatProperResultWasReturned("((2.4-0.4) * 3)^2", 36);
        assertThatProperResultWasReturned("((2.4-(0.4 + 1)) * 3)^2", 9);
    }

    private void assertThatProperResultWasReturned(String operation, double expectedResult) {
        assertEquals(calculator.calculate(operation), expectedResult);
    }
}
