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
public class AccountConverterTest {

    private static final String IBAN = "12345";

    private AccountConverter accountConverter;

    @Mock
    private AccountService accountServiceMock;

    @Before
    public void setup() {
        accountConverter = new AccountConverter(accountServiceMock);
    }

    @Test
    public void getAsStringShouldReturnNullWhenNullIsPassed() {
        String result = accountConverter.getAsString(null, null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void getAsStringShouldReturnTheAccountsIban() {
        Account account = new Account();
        account.setIban(IBAN);
        String result = accountConverter.getAsString(null, null, account);
        assertThat(result, is(IBAN));
    }

    @Test
    public void getAsObjectShouldCallGetAccountByIbanWithTheCorrectArgument() {
        String iban = IBAN;
        accountConverter.getAsObject(null, null, iban);
        verify(accountServiceMock).getAccountByIban(iban);
    }
}
