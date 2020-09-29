import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import edu.csc413.calculator.evaluator.Operand;
import org.junit.jupiter.api.Test;

public class OperandTest {
    @Test
    public void getValue_fromInt() {
        Operand operand = new Operand(6);
        assertThat(operand.getValue(), equalTo(6));
    }

    @Test
    public void getValue_fromInt_negative() {
        Operand operand = new Operand(-3);
        assertThat(operand.getValue(), equalTo(-3));
    }

    @Test
    public void getValue_fromString() {
        Operand operand = new Operand("7");
        assertThat(operand.getValue(), equalTo(7));
    }

    @Test
    public void getValue_fromString_negative() {
        Operand operand = new Operand("-8");
        assertThat(operand.getValue(), equalTo(-8));
    }

    @Test
    public void isValid_true() {
        assertThat(Operand.isValid("13"), equalTo(true));
    }

    @Test
    public void isValid_true_negative() {
        assertThat(Operand.isValid("-5"), equalTo(true));
    }

    @Test
    public void isValid_false_letter() {
        assertThat(Operand.isValid("g"), equalTo(false));
    }

    @Test
    public void isValid_false_specialCharacter() {
        assertThat(Operand.isValid("&"), equalTo(false));
    }

    @Test
    public void isValid_false_decimal() {
        assertThat(Operand.isValid("1.5"), equalTo(false));
    }
}
