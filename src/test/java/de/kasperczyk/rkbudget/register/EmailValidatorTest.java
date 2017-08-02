package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.user.UserService;
import de.kasperczyk.rkbudget.util.FacesContextMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTest {

    private static final String VALID_EMAIL = "kasperczyk.rene@gmail.com";
    private static final String INVALID_EMAIL = "invalid@mail";

    private EmailValidator emailValidator;

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private RegisterController registerControllerMock;

    @Mock
    private UserService userServiceMock;

    private FacesContext facesContextMock;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        emailValidator = new EmailValidator(messageSourceMock, registerControllerMock, userServiceMock);
        facesContextMock = FacesContextMock.getMock();
    }

    @Test
    public void validateShouldNotThrowAnExceptionIfValidEmailIsProvided() {
        emailValidator.validate(facesContextMock, null, VALID_EMAIL);
    }

    @Test
    public void validateShouldThrowAnExceptionIfInvalidEmailIsProvided() {
        expectedException.expect(ValidatorException.class);
        emailValidator.validate(facesContextMock, null, INVALID_EMAIL);
    }

    @Test
    public void validateShouldNotThrowAnExceptionIfEmailIsNotTaken() {
        when(userServiceMock.emailExists(VALID_EMAIL)).thenReturn(false);
        emailValidator.validate(facesContextMock, null, VALID_EMAIL);
    }

    @Test
    public void validateShouldThrowAnExceptionIfEmailIsAlreadyTaken() {
        when(userServiceMock.emailExists(VALID_EMAIL)).thenReturn(true);
        expectedException.expect(ValidatorException.class);
        emailValidator.validate(facesContextMock, null, INVALID_EMAIL);
    }
}
