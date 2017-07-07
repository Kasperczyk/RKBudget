package de.kasperczyk.rkbudget.account;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private AccountController accountController;

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private AccountService accountServiceMock;

    @Before
    public void setup() {
        accountController = new AccountController(messageSourceMock, accountServiceMock);
    }

    @Test
    public void accountsShouldHaveBeenInitialized() {
        assertThat(accountController.getAccounts(), is(not(nullValue())));
    }

    @Test
    public void addAccountShouldAddTheAccountToTheAccountsList() {
        assertThat(accountController.getAccounts().size(), is(0));
        accountController.addAccount();
        assertThat(accountController.getAccounts().size(), is(1));
    }

    @Test
    public void addAccountShouldCallAddAccountWithTheCorrectArgument() {
        Date expirationDate = new Date();
        Account account = new Account(AccountType.GIRO_ACCOUNT, "Institute",
                "Owner", "IBAN", expirationDate, BigDecimal.ONE);
        accountController.setAccountType(account.getAccountType());
        accountController.setInstitute(account.getInstitute());
        accountController.setOwner(account.getOwner());
        accountController.setIban(account.getIban());
        accountController.setExpirationDate(expirationDate);
        accountController.setBalance(account.getBalance());

        accountController.addAccount();

        verify(accountServiceMock).addAccount(account);
    }

    @Test
    public void getAccountTypeNameShouldCallGetMessageWithTheKeyAsAnArgument() {
        AccountType accountType = AccountType.GIRO_ACCOUNT;
        accountController.getAccountTypeName(accountType);
        verify(messageSourceMock).getMessage(accountType.getKey(), null, new Locale("de"));
    }

    @Test
    public void getAccountTypesShouldCallGetAccountTypes() {
        accountController.getAccountTypes();
        verify(accountServiceMock).getAccountTypes();
    }
}
