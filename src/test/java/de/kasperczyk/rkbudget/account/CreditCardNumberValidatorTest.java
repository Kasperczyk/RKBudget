package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.UserController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import javax.faces.validator.ValidatorException;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardNumberValidatorTest {

    private static final String INVALID_CREDIT_CARD_NUMBER = "345 4961 1512 2611";
    private static final String VALID_CREDIT_CARD_NUMBER = "3454 9651 5132 631";

    private CreditCardNumberValidator creditCardNumberValidator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private UserController userControllerMock;

    @Before
    public void setup() {
        this.creditCardNumberValidator = new CreditCardNumberValidator(messageSourceMock, userControllerMock);
    }

    @Test
    public void creditCardNumberValidatorShouldThrowExceptionIfCreditCardNumberIsInvalid() {
        expectedException.expect(ValidatorException.class);
        creditCardNumberValidator.validate(null, null, INVALID_CREDIT_CARD_NUMBER);
    }

    @Test
    public void creditCardNumberValidatorShouldNotThrowExceptionIfCreditCardNumberIsValid() {
        creditCardNumberValidator.validate(null, null, VALID_CREDIT_CARD_NUMBER);
    }
}
