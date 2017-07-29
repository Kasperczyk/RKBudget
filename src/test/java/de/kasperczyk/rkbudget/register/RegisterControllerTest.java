package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.language.Language;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.util.FacesContextMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    private static final String GERMAN_IP = "88.198.21.11";

    private RegisterController registerController;

    @Mock
    private RegisterService registerServiceMock;

    @Before
    public void setup() {
        registerController = new RegisterController(registerServiceMock);
    }

    @Test
    public void registerShouldActuallyRegister() {
        setupMocksForRegister(GERMAN_IP);
        registerController.register();
        verify(registerServiceMock).register(any(User.class));
    }

    @Test
    public void registerShouldGetTheInitialCurrencyByIp() {
        setupMocksForRegister(GERMAN_IP);
        registerController.register();
        verify(registerServiceMock).getInitialCurrencyByIp(GERMAN_IP);
    }

    @Test
    public void registerShouldEncodeThePassword() {
        setupMocksForRegister(GERMAN_IP);
        registerController.setPassword("password");
        registerController.register();
        verify(registerServiceMock).encodePassword("password");
    }

    @Test
    public void registerShouldSetSubmittedToTrue() {
        setupMocksForRegister(GERMAN_IP);
        registerController.register();
        assertThat(registerController.isSubmitted(), is(true));
    }

    @Test
    public void registerShouldSetRegisteredToTrueAndResetFieldsIfRegistrationIsSuccessful() {
        setupFields();
        setupMocksForRegister(GERMAN_IP);
        when(registerServiceMock.register(any(User.class))).thenReturn(true);
        registerController.register();
        assertThat(registerController.isRegistered(), is(true));
        assertThat(areFieldsNull(), is(true));
    }

    @Test
    public void registerShouldSetRegisteredToFalseAndNotResetFieldsIfRegistrationIsSuccessful() {
        setupFields();
        setupMocksForRegister(GERMAN_IP);
        when(registerServiceMock.register(any(User.class))).thenReturn(false);
        registerController.register();
        assertThat(registerController.isRegistered(), is(false));
        assertThat(areFieldsNull(), is(false));
    }

    private void setupFields() {
        registerController.setFirstName("Rene");
        registerController.setLastName("Kasperczyk");
        registerController.setUserName("Vyrwel");
        registerController.setEmail("rene@test.de");
    }

    private boolean areFieldsNull() {
        return registerController.getFirstName() == null &&
                registerController.getLastName() == null &&
                registerController.getUserName() == null &&
                registerController.getEmail() == null;
    }

    private void setupMocksForRegister(String desiredHeaderReturnValue) {
        FacesContext facesContextMock = FacesContextMock.getMock();
        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);
        when(facesContextMock.getExternalContext().getRequest()).thenReturn(httpServletRequestMock);
        when(httpServletRequestMock.getHeader("X-FORWARDED-FOR")).thenReturn(desiredHeaderReturnValue);
        when(facesContextMock.getExternalContext().getRequestLocale()).thenReturn(new Locale("de"));
    }

    @Test
    public void getLocaleShouldReturnTheLocaleCorrespondingToTheSelectedLanguage() {
        registerController.setLanguage(Language.GERMAN);
        assertThat(registerController.getLocale(), is(new Locale("de")));
    }

    @Test
    public void getLocaleShouldReturnTheRequestLocaleIfLanguageIsNull() {
        FacesContext facesContextMock = FacesContextMock.getMock();
        when(facesContextMock.getExternalContext().getRequestLocale()).thenReturn(new Locale("de"));
        assertThat(registerController.getLocale(), is(new Locale("de")));
    }

    @Test
    public void verifyShouldVerifyAGivenToken() {
        registerController.setToken("token");
        registerController.verify();
        verify(registerServiceMock).verify("token");
    }

    @Test
    public void verifyShouldSetVerifiedToTrueAfterSuccessfulVerification() {
        registerController.setToken("token");
        when(registerServiceMock.verify("token")).thenReturn(true);
        registerController.verify();
        assertThat(registerController.isVerified(), is(true));
    }

    @Test
    public void verifyShouldSetVerifiedToFalseAfterUnsuccessfulVerification() {
        registerController.setToken("token");
        when(registerServiceMock.verify("token")).thenReturn(false);
        registerController.verify();
        assertThat(registerController.isVerified(), is(false));
    }

    @Test
    public void verifyShouldDoNothingIfTokenIsNull() {
        registerController.verify();
        verify(registerServiceMock, never()).verify(anyString());
    }
}
