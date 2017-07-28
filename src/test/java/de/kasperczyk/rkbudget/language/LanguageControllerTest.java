package de.kasperczyk.rkbudget.language;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LanguageControllerTest {

    private LanguageController languageController;

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private LanguageService languageServiceMock;

    @Mock
    private UserController userControllerMock;

    @Before
    public void setup() {
        languageController = new LanguageController(messageSourceMock, languageServiceMock, userControllerMock);
    }

    @Test
    public void getLanguageByNameShouldBaseTheLocaleOnTheLanguagePropertyIfCurrentUserIsNull() {
        when(userControllerMock.getCurrentUser()).thenReturn(null);
        Language language = Language.ENGLISH;
        languageController.getLanguageName(language);
        verify(messageSourceMock).getMessage(language.getMessageKey(),
                                             null, new Locale(language.getCountryCode()));
    }

    @Test
    public void getLanguageByNameShouldBaseTheLocaleOnTheUsersLanguageSettingIfCurrentUserIsNotNull() {
        User user = new User();
        user.setLocale(new Locale("en"));
        when(userControllerMock.getCurrentUser()).thenReturn(user);
        Language language = Language.ENGLISH;
        languageController.getLanguageName(language);
        verify(messageSourceMock).getMessage(language.getMessageKey(),
                                             null, user.getLocale());
    }
}
