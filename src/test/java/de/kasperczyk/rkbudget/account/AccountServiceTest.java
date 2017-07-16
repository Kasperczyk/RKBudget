package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
                "CCN", null, new Date(), BigDecimal.ONE, new User());
        accountService.addAccount(account);
        verify(accountRepositoryMock).save(account);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void accountExistsShouldReturnTrueIfAnAccountIsFound() {
        List<Account> dummyList = new ArrayList<>();
        dummyList.add(new Account());
        when(accountRepositoryMock.findAll(any(Example.class))).thenReturn(dummyList);
        assertThat(accountService.accountExists(new Account()), is(true));
    }

    @Test
    public void accountExistsShouldReturnFalseIfNoAccountIsFound() {
        when(accountRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        assertThat(accountService.accountExists(new Account()), is(false));
    }
}
