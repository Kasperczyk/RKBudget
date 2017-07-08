package de.kasperczyk.rkbudget.account;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private static final int LIMIT = 3;

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
    public void listsAndMapsShouldBeInitialized() {
        assertThat(accountController.getAccounts(), is(not(nullValue())));
        assertThat(accountController.getAccountsChecked(), is(not(nullValue())));
    }

    @Test
    public void addAccountShouldAddTheAccountToTheAccountsList() {
        assertThat(accountController.getAccounts().size(), is(0));
        addAccountAndFillId(1);
        assertThat(accountController.getAccounts().size(), is(1));
    }

    @Test
    public void addAccountShouldSetCheckedToTrueWhenLimitIsNotReached() {
        Map<Long, Boolean> accountsChecked = accountController.getAccountsChecked();
        assertThat(accountsChecked.size(), is(0));
        addAccountAndFillId(LIMIT);
        assertThat(accountsChecked.size(), is(3));
        for (Boolean isChecked : accountsChecked.values()) {
            assertThat(isChecked, is(true));
        }
    }

    @Test
    public void addAccountShouldSetCheckedToFalseWhenLimitIsReached() {
        assertThat(accountController.getAccountsChecked().size(), is(0));
        addAccountAndFillId(LIMIT + 2);
        for (long i = 0; i < accountController.getAccountsChecked().size(); i++) {
           if (i < 3) {
               assertThat(accountController.getAccountsChecked().get(i), is(true));
           } else {
               assertThat(accountController.getAccountsChecked().get(i), is(false));
           }
        }
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

        addAccountAndFillId(1);

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

    @Test
    public void isCheckboxDisabledShouldReturnTrueWhenLimitIsReached() {
        addAccountAndFillId(4);
        assertThat(accountController.isCheckboxDisabled(3L), is(true));
    }

    @Test
    public void getShownAccountsShouldOnlyReturnShownAccounts() {
        addAccountAndFillId(4);
        List<Account> shownAccounts = accountController.getShownAccounts();
        assertThat(shownAccounts.size(), is(3));
        for (Account shownAccount : shownAccounts) {
            assertThat(accountController.getAccountsChecked().get(shownAccount.getId()), is(true));
        }
    }

    private void addAccountAndFillId(int times) {
        for (long i = 0; i < times; i++) {
            accountController.addAccount();
            boolean checked = accountController.getAccountsChecked().get(null);
            accountController.getAccountsChecked().remove(null);
            accountController.getAccountsChecked().put(i, checked);
            accountController.getAccounts().get((int) i).setId(i);
        }
    }
}
