package de.kasperczyk.rkbudget.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private UserController userController;

    @Mock
    private Environment environmentMock;

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setup() {
        when(environmentMock.getActiveProfiles()).thenReturn(new String[]{});
        userController = new UserController(environmentMock, messageSourceMock, userServiceMock);
    }

    @Test
    public void saveShouldSetTheLocaleCorrectlyAndCallUpdateUser() {
        userController.setLanguage(Language.GERMAN);
        userController.setCurrentUser(new User());
        userController.save();
        verify(userServiceMock).updateUser(userController.getCurrentUser());
        assertThat(userController.getCurrentUser().getLocale(), is(new Locale("de")));
    }
}
