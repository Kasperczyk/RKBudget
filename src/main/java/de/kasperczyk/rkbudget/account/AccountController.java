package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserController;
import de.kasperczyk.rkbudget.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Scope("session")
public class AccountController {

    // todo extend in the future, maybe allow the user to choose from several layouts
    private static final int SHOWN_ACCOUNTS_LIMIT = 3;

    private final MessageSource messageSource;
    private final AccountService accountService;
    private final UserController userController;
    private final UserService userService;

    private List<Account> accounts;
    private Map<Long, Boolean> accountsChecked;

    private AccountType accountType;
    private String name;
    private String institute;
    private String owner;
    private String iban;
    private String creditCardNumber;
    private Date expirationDate; // todo Java 8 LocalDate
    private BigDecimal balance;
    private Boolean linkedToAnotherAccount;
    private Account linkedAccount;

    @Autowired
    public AccountController(MessageSource messageSource,
                             AccountService accountService,
                             UserController userController,
                             UserService userService) {
        this.messageSource = messageSource;
        this.accountService = accountService;
        this.userController = userController;
        this.userService = userService;
        accounts = new ArrayList<>();
        accountsChecked = new HashMap<>();
    }

    public List<AccountType> getAllAccountTypes() {
        return accountService.getAllAccountTypes();
    }

    public String getAccountTypeName(AccountType accountType) {
        return messageSource.getMessage(accountType.getKey(), null, userController.getLocale());
    }

    public boolean isBankAccountOrNull() {
        return accountType == null || accountType == AccountType.GIRO || accountType == AccountType.SAVINGS;
    }

    public boolean isCreditCard() {
        return accountType == AccountType.CREDIT;
    }

    public boolean isCash() {
        return accountType == AccountType.CASH;
    }

    public boolean isCustom() {
        return accountType == AccountType.CUSTOM;
    }

    public List<User> suggestOwner(String query) {
        // todo limit by something (own user and "friends")
        List<User> allUsers = userService.getAllUsers();
        return allUsers.stream()
                .filter(user -> user.getFullName().toLowerCase().startsWith(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Account> getLinkableAccounts() {
        return accountService.getLinkableAccounts();
    }

    public void addAccount() {
        User currentUser = userController.getCurrentUser();
        Account account = new Account(accountType, name, institute, owner, iban, expirationDate, balance, currentUser);
        if (!accountService.accountExists(account)) {
            accountService.addAccount(account);
            accountsChecked.put(account.getId(), !isLimitReached());
            accounts.add(account);
        } else {
            String message = messageSource.getMessage("account_error_accountAlreadyAdded", null, userController.getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        }
        resetFields();
    }

    private void resetFields() {
        name = null;
        institute = null;
        owner = null;
        iban = null;
        creditCardNumber = null;
        expirationDate = null;
        balance = null;
    }

    private Boolean isLimitReached() {
        return accountsChecked.values()
                .stream()
                .filter(checked -> checked)
                .count() == SHOWN_ACCOUNTS_LIMIT;
    }

    public boolean isCheckboxDisabled(Long id) {
        return !accountsChecked.get(id) && isLimitReached();
    }

    public List<Account> getShownAccounts() {
        return accounts.stream()
                .filter(account -> accountsChecked.get(account.getId()))
                .collect(Collectors.toList());
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getLinkedToAnotherAccount() {
        return linkedToAnotherAccount;
    }

    public void setLinkedToAnotherAccount(Boolean linkedToAnotherAccount) {
        this.linkedToAnotherAccount = linkedToAnotherAccount;
    }

    public Account getLinkedAccount() {
        return linkedAccount;
    }

    public void setLinkedAccount(Account linkedAccount) {
        this.linkedAccount = linkedAccount;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Map<Long, Boolean> getAccountsChecked() {
        return accountsChecked;
    }
}
