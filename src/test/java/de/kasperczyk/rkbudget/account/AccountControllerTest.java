package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserController;
import de.kasperczyk.rkbudget.user.UserService;
import de.kasperczyk.rkbudget.util.FacesContextMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private static final int LIMIT = 3;

    private AccountController accountController;

    @Mock
    private MessageSource messageSourceMock;

    @Mock
    private AccountService accountServiceMock;

    @Mock
    private UserController userControllerMock;

    @Mock
    private UserService userServiceMock;

    @Before
    public void setup() {
        accountController = new AccountController(messageSourceMock, accountServiceMock, userControllerMock, userServiceMock);
    }

    @Test
    public void objectsShouldBeInitialized() {
        assertThat(accountController.getAccounts(), is(not(nullValue())));
        assertThat(accountController.getAccountsChecked(), is(not(nullValue())));
        assertThat(accountController.getEditMode(), is(not(nullValue())));
    }

    @Test
    public void getAllAccountTypesShouldCallGetAllAccountTypes() {
        accountController.getAllAccountTypes();
        verify(accountServiceMock).getAllAccountTypes();
    }

    @Test
    public void getAccountTypeNameShouldCallGetMessageWithTheKeyAsAnArgument() {
        AccountType accountType = AccountType.GIRO;
        accountController.getAccountTypeName(accountType);
        verify(messageSourceMock).getMessage(accountType.getKey(), null, null);
    }

    @Test
    public void isBankAccountOrNullShouldReturnTheCorrectBooleanValue() {
        accountController.setAccountType(null);
        assertThat(accountController.isBankAccountOrNull(), is(true));

        accountController.setAccountType(AccountType.GIRO);
        assertThat(accountController.isBankAccountOrNull(), is(true));

        accountController.setAccountType(AccountType.SAVINGS);
        assertThat(accountController.isBankAccountOrNull(), is(true));

        accountController.setAccountType(AccountType.CASH);
        assertThat(accountController.isBankAccountOrNull(), is(false));

        accountController.setAccountType(AccountType.CREDIT);
        assertThat(accountController.isBankAccountOrNull(), is(false));

        accountController.setAccountType(AccountType.CUSTOM);
        assertThat(accountController.isBankAccountOrNull(), is(false));
    }

    @Test
    public void isCreditCardShouldReturnTheCorrectBooleanValue() {
        accountController.setAccountType(AccountType.GIRO);
        assertThat(accountController.isCreditCard(), is(false));

        accountController.setAccountType(AccountType.SAVINGS);
        assertThat(accountController.isCreditCard(), is(false));

        accountController.setAccountType(AccountType.CASH);
        assertThat(accountController.isCreditCard(), is(false));

        accountController.setAccountType(AccountType.CREDIT);
        assertThat(accountController.isCreditCard(), is(true));

        accountController.setAccountType(AccountType.CUSTOM);
        assertThat(accountController.isCreditCard(), is(false));
    }

    @Test
    public void isCashShouldReturnTheCorrectBooleanValue() {
        accountController.setAccountType(AccountType.GIRO);
        assertThat(accountController.isCash(), is(false));

        accountController.setAccountType(AccountType.SAVINGS);
        assertThat(accountController.isCash(), is(false));

        accountController.setAccountType(AccountType.CASH);
        assertThat(accountController.isCash(), is(true));

        accountController.setAccountType(AccountType.CREDIT);
        assertThat(accountController.isCash(), is(false));

        accountController.setAccountType(AccountType.CUSTOM);
        assertThat(accountController.isCash(), is(false));
    }

    @Test
    public void isCustomShouldReturnTheCorrectBooleanValue() {
        accountController.setAccountType(AccountType.GIRO);
        assertThat(accountController.isCustom(), is(false));

        accountController.setAccountType(AccountType.SAVINGS);
        assertThat(accountController.isCustom(), is(false));

        accountController.setAccountType(AccountType.CASH);
        assertThat(accountController.isCustom(), is(false));

        accountController.setAccountType(AccountType.CREDIT);
        assertThat(accountController.isCustom(), is(false));

        accountController.setAccountType(AccountType.CUSTOM);
        assertThat(accountController.isCustom(), is(true));
    }

    @Test
    public void suggestOwnerShouldCallGetAllUsers() {
        accountController.suggestOwner("query");
        verify(userServiceMock).getAllUsers();
    }

    @Test
    public void suggestOwnerShouldFilterByThePassedQuery() {
        setupSuggestionUserList();
        assertThat(accountController.suggestOwner("Abc").size(), is(0));
        assertThat(accountController.suggestOwner("Re").size(), is(1));
    }

    @Test
    public void suggestOwnerShouldIgnoreLowerAndUpperCase() {
        setupSuggestionUserList();
        assertThat(accountController.suggestOwner("rEnE kAs").size(), is(1));
    }

    private void setupSuggestionUserList() {
        List<User> users = new ArrayList<>();
        users.add(new User() {{
            setFirstName("Rene");
            setLastName("Kasperczyk");
        }});
        when(userServiceMock.getAllUsers()).thenReturn(users);
    }

    @Test
    public void getLinkableAccountsShouldCallGetLinkableAccounts() {
        accountController.getLinkableAccounts();
        verify(accountServiceMock).getLinkableAccounts();
    }

    @Test
    public void resetFieldsShouldSetAllInputFieldsToNullAndSetEditModeToFalse() {
        accountController.setAccountType(AccountType.CUSTOM);
        accountController.setName("Name");
        accountController.setInstitute("Institute");
        accountController.setOwner("Owner");
        accountController.setIban("Iban");
        accountController.setCreditCardNumber("CCN");
        accountController.setLinkedToAnotherAccount(true);
        accountController.setExpirationDate(new Date());
        accountController.setBalance(BigDecimal.ZERO);
        accountController.setEditMode(true);

        accountController.resetFields();

        assertThat(accountController.getAccountType(), is(nullValue()));
        assertThat(accountController.getName(), is(nullValue()));
        assertThat(accountController.getInstitute(), is(nullValue()));
        assertThat(accountController.getOwner(), is(nullValue()));
        assertThat(accountController.getIban(), is(nullValue()));
        assertThat(accountController.getCreditCardNumber(), is(nullValue()));
        assertThat(accountController.getLinkedToAnotherAccount(), is(nullValue()));
        assertThat(accountController.getExpirationDate(), is(nullValue()));
        assertThat(accountController.getBalance(), is(nullValue()));
        assertThat(accountController.getEditMode(), is(false));
    }

    @Test
    public void saveAccountShouldCallCreateAccountWhenNotInEditMode() {
        accountController.setEditMode(false);
        accountController.saveAccount();
        verify(accountServiceMock).saveAccount(Matchers.any(Account.class));
    }

    @Test
    public void createAccountShouldAddTheAccountToTheAccountsList() {
        assertThat(accountController.getAccounts().size(), is(0));
        accountController.createAccount(new Account());
        assertThat(accountController.getAccounts().size(), is(1));
    }

    @Test
    public void createAccountShouldNotAddAnAccountThatAlreadyExistsAndAddAnErrorMessage() {
        when(accountServiceMock.accountExists(Matchers.any(Account.class))).thenReturn(true);
        FacesContext facesContextMock = FacesContextMock.getMock();
        accountController.createAccount(new Account());
        verify(facesContextMock).addMessage(anyObject(), Matchers.any(FacesMessage.class));
    }

    @Test
    public void createAccountShouldCallAddAccountWithTheCorrectArgument() {
        Account account = getDummyAccount();
        accountController.setAccountType(account.getAccountType());
        accountController.setName(account.getName());
        accountController.setInstitute(account.getInstitute());
        accountController.setOwner(account.getOwner());
        accountController.setIban(account.getIban());
        accountController.setCreditCardNumber(account.getCreditCardNumber());
        accountController.setExpirationDate(account.getExpirationDate());
        accountController.setBalance(account.getBalance());
        when(userControllerMock.getCurrentUser()).thenReturn(account.getUser());

        accountController.saveAccount();

        verify(accountServiceMock).saveAccount(account);
    }

    private Account getDummyAccount() {
        Date expirationDate = new Date();
        User user = new User();
        return new Account(AccountType.GIRO, "Name", "Institute", "Owner", "IBAN",
                    "CCN", null, expirationDate, BigDecimal.ONE, user);
    }

    @Test
    public void createAccountShouldSetCheckedToTrueWhenLimitIsNotReached() {
        Map<Long, Boolean> accountsChecked = accountController.getAccountsChecked();
        assertThat(accountsChecked.size(), is(0));
        saveAccountsAndFillIds(LIMIT);
        assertThat(accountsChecked.size(), is(3));
        for (Boolean isChecked : accountsChecked.values()) {
            assertThat(isChecked, is(true));
        }
    }

    @Test
    public void addAccountShouldSetCheckedToFalseWhenLimitIsReached() {
        Map<Long, Boolean> accountsChecked = accountController.getAccountsChecked();
        assertThat(accountsChecked.size(), is(0));
        saveAccountsAndFillIds(LIMIT + 2);
        assertThat(accountsChecked.size(), is(5));
        for (int i = 0; i < accountsChecked.size(); i++) {
            if (i < 3) {
                assertThat(accountsChecked.get((long) i), is(true));
            } else {
                assertThat(accountsChecked.get((long) i), is(false));
            }
        }
    }

    private void saveAccountsAndFillIds(int noOfAccounts) {
        for (int i = 0; i < noOfAccounts; i++) {
            accountController.saveAccount();
            boolean checked = accountController.getAccountsChecked().get(null);
            accountController.getAccountsChecked().remove(null);
            accountController.getAccountsChecked().put((long) i, checked);
            accountController.getAccounts().get((int) i).setId((long) i);
        }
    }

    @Test
    public void saveAccountShouldNotCallCreateAccountWhenInEditMode() {
        setupSaveAccount();
        accountController.saveAccount();
        verify(accountServiceMock, never()).saveAccount(Matchers.any(Account.class));
    }

    @Test
    public void saveAccountShouldCallResetFields() {
        prepareResetFieldsTest(true);
        assertThat(accountController.getIban(), is(nullValue()));
        prepareResetFieldsTest(false);
        assertThat(accountController.getIban(), is(nullValue()));
    }

    private void prepareResetFieldsTest(boolean editMode) {
        accountController.setIban("control sample");
        accountController.setEditMode(editMode);
        if (editMode) {
            setupSaveAccount();
        }
        accountController.saveAccount();
    }

    @Test
    public void saveAccountShouldCallUpdateAccountWithTheCorrectArguments() {
        setupSaveAccount();
        accountController.saveAccount();
        verify(accountServiceMock).updateAccount(new Account(), 1L);
    }

    @Test
    public void saveAccountShouldReplaceTheAccountInTheListWhenUpdating() {
        setupSaveAccount();
        assertThat(accountController.getAccounts().get(0).getBalance(), is(new BigDecimal(100)));
        accountController.setBalance(new BigDecimal(200));
        accountController.saveAccount();
        assertThat(accountController.getAccounts().get(0).getBalance(), is(new BigDecimal(200)));
    }

    private void setupSaveAccount() {
        long id = 1L;
        accountController.getAccounts().add(new Account() {{
            setId(id);
            setBalance(new BigDecimal(100));
        }});
        accountController.setId(id);
        accountController.setEditMode(true);
    }

    // todo: test getAccountTitle()

    @Test
    public void selectAccountShouldSetTheInputFieldsCorrectlyAndEditModeToTrue() {
        Account account = getDummyAccount();
        account.setId(1L);
        accountController.selectAccount(account);
        assertThat(accountController.getId(), is(account.getId()));
        assertThat(accountController.getAccountType(), is(account.getAccountType()));
        assertThat(accountController.getName(), is(account.getName()));
        assertThat(accountController.getInstitute(), is(account.getInstitute()));
        assertThat(accountController.getOwner(), is(account.getOwner()));
        assertThat(accountController.getIban(), is(account.getIban()));
        assertThat(accountController.getCreditCardNumber(), is(account.getCreditCardNumber()));
        assertThat(accountController.getLinkedAccount(), is(account.getLinkedAccount()));
        assertThat(accountController.getExpirationDate(), is(account.getExpirationDate()));
        assertThat(accountController.getBalance(), is(account.getBalance()));
        assertThat(accountController.getEditMode(), is(true));
    }

    @Test
    public void deleteAccountShouldRemoveTheAccountFromTheListAndTheDatabaseAndResetInputFields() {
        Account account = getDummyAccount();
        account.setId(1L);
        accountController.setIban("control sample");
        accountController.getAccounts().add(account);

        accountController.deleteAccount(account);

        assertThat(accountController.getAccounts().size(), is(0));
        verify(accountServiceMock).deleteAccount(1L);
        assertThat(accountController.getIban(), is(nullValue()));
    }

    @Test
    public void isCheckboxDisabledShouldReturnTrueWhenLimitIsReached() {
        saveAccountsAndFillIds(4);
        assertThat(accountController.isCheckboxDisabled(3L), is(true));
    }

    @Test
    public void getShownAccountsShouldOnlyReturnShownAccounts() {
        saveAccountsAndFillIds(4);
        List<Account> shownAccounts = accountController.getShownAccounts();
        assertThat(shownAccounts.size(), is(3));
        for (Account shownAccount : shownAccounts) {
            assertThat(accountController.getAccountsChecked().get(shownAccount.getId()), is(true));
        }
    }
}
