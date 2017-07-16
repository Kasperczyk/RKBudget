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
public class IbanValidatorTest {

    private static final String INVALID_IBAN = "DE22 5001 0517 5418 9200 27";
    private static final String VALID_IBAN = "DE22 5001 0517 5418 9200 28";

    private IbanValidator ibanValidator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private UserController userControllerMock;

    @Before
    public void setup() {
        this.ibanValidator = new IbanValidator(messageSourceMock, userControllerMock);
    }

    @Test
    public void ibanValidatorShouldThrowExceptionIfIbanIsInvalid() {
        expectedException.expect(ValidatorException.class);
        ibanValidator.validate(null, null, INVALID_IBAN);
    }

    @Test
    public void ibanValidatorShouldNotThrowExceptionIfIbanIsValid() {
        ibanValidator.validate(null, null, VALID_IBAN);
    }
}
