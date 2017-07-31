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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserNameValidatorTest {

    private UserNameValidator userNameValidator;

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
        userNameValidator = new UserNameValidator(messageSourceMock, registerControllerMock, userServiceMock);
        facesContextMock = FacesContextMock.getMock();
    }

    @Test
    public void validateShouldNotThrowAnExceptionIfUserNameItNotTaken() {
        when(userServiceMock.isUserNameTaken(anyString())).thenReturn(false);
        userNameValidator.validate(facesContextMock, null, "userName");
    }

    @Test
    public void validateShouldThrowAnExceptionIfUserNameIsTaken() {
        when(userServiceMock.isUserNameTaken(anyString())).thenReturn(true);
        expectedException.expect(ValidatorException.class);
        userNameValidator.validate(facesContextMock, null, "userName");
    }
}
