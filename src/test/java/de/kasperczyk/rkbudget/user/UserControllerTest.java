package de.kasperczyk.rkbudget.user;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private UserController userController;

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setup() {
        userController = new UserController(messageSourceMock, userServiceMock);
    }

    @Test
    public void saveShouldSetTheLocaleCorrectlyAndCallUpdateUser() {
        userController.setLanguage(Language.GERMAN);
        userController.setCurrency(Currency.EURO);
        userController.setCurrentUser(new User());
        userController.save();
        verify(userServiceMock).updateUser(userController.getCurrentUser());
        assertThat(userController.getCurrentUser().getLocale(), is(new Locale("de")));
        assertThat(userController.getCurrentUser().getCurrency(), is(Currency.EURO));
    }
}
