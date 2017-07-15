package de.kasperczyk.rkbudget.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LanguageConverterTest {

    private LanguageConverter languageConverter;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setup() {
        languageConverter = new LanguageConverter(userServiceMock);
    }

    @Test
    public void getAsStringShouldReturnNullWhenNullIsPassed() {
        String result = languageConverter.getAsString(null, null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void getAsStringShouldReturnTheAccountTypesName() {
        String result = languageConverter.getAsString(null, null, Language.ENGLISH);
        assertThat(result, is(Language.ENGLISH.name()));
    }

    @Test
    public void getAsObjectShouldCallGetLanguageByNameWithTheCorrectArgument() {
        String name = Language.ENGLISH.name();
        languageConverter.getAsObject(null, null, name);
        verify(userServiceMock).getLanguageByName(name);
    }
}
