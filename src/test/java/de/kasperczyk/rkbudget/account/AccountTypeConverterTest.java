package de.kasperczyk.rkbudget.account;

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
public class AccountTypeConverterTest {

    private AccountTypeConverter accountTypeConverter;

    @Mock
    private AccountService accountServiceMock;

    @Before
    public void setup() {
        accountTypeConverter = new AccountTypeConverter(accountServiceMock);
    }

    @Test
    public void getAsStringShouldReturnNullWhenNullIsPassed() {
        String result = accountTypeConverter.getAsString(null, null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void getAsStringShouldReturnTheAccountTypesName() {
        String result = accountTypeConverter.getAsString(null, null, AccountType.GIRO);
        assertThat(result, is(AccountType.GIRO.name()));
    }

    @Test
    public void getAsObjectShouldCallGetAccountTypeByNameWithTheCorrectArgument() {
        String name = AccountType.GIRO.name();
        accountTypeConverter.getAsObject(null, null, name);
        verify(accountServiceMock).getAccountTypeByName(name);
    }
}
