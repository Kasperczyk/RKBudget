package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepositoryMock;

    @Before
    public void setup() {
        accountService = new AccountService(accountRepositoryMock);
    }

    @Test
    public void getAccountTypesShouldReturnAllAccountTypesAsAList() {
        assertThat(accountService.getAllAccountTypes(), is(Arrays.asList(AccountType.values())));
    }

    @Test
    public void getAccountTypeByNameShouldReturnTheCorrectAccountType() {
        assertThat(accountService.getAccountTypeByName(AccountType.GIRO.name()), is(AccountType.GIRO));
    }

    @Test
    public void addAccountShouldCallSaveWithTheCorrectArgument() {
        Account account = new Account(AccountType.GIRO, "Name", "Institute", "Owner", "IBAN",
                new Date(), BigDecimal.ONE, new User());
        accountService.addAccount(account);
        verify(accountRepositoryMock).save(account);
    }
}
