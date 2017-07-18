package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserController;
import org.apache.commons.lang3.StringUtils;
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

    private Long id;
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

    private Boolean editMode;
    private int editIndex = -1;

    private List<Account> accounts;
    private Map<Long, Boolean> accountsChecked;

    @Autowired
    public AccountController(MessageSource messageSource,
                             AccountService accountService,
                             UserController userController) {
        this.messageSource = messageSource;
        this.accountService = accountService;
        this.userController = userController;
        accounts = new ArrayList<>();
        accountsChecked = new HashMap<>();
        editMode = false;
    }


    // create-edit-account.xhtml
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
        List<User> allUsers = accountService.getAllUsers();
        return allUsers.stream()
                .filter(user -> user.getFullName().toLowerCase().startsWith(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Account> getLinkableAccounts() {
        return accountService.getLinkableAccounts();
    }

    public void resetFields() {
        accountType = null;
        name = null;
        institute = null;
        owner = null;
        iban = null;
        creditCardNumber = null;
        expirationDate = null;
        linkedToAnotherAccount = null;
        balance = null;
        editMode = false;
        editIndex = -1;
    }

    public void saveAccount() {
        Account account = new Account(accountType, name, institute, owner, iban, creditCardNumber,
                linkedAccount, expirationDate, balance, userController.getCurrentUser());
        if (editMode) {
            accountService.updateAccount(account, id);
            account.setId(id);
            accounts.set(getAccountIndexById(id), account);
        } else {
            createAccount(account);
        }
        resetFields();
    }

    private int getAccountIndexById(long id) {
        Account account = accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find account with id " + id));
        return accounts.indexOf(account);
    }

    void createAccount(Account account) {
        if (!accountService.accountExists(account)) {
            accountService.saveAccount(account);
            accountsChecked.put(account.getId(), !isLimitReached());
            accounts.add(account);
        } else {
            String message = messageSource.getMessage("account_error_accountAlreadyAdded", null,
                    userController.getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        }
    }

    public void removeBalance() {
        balance = null;
    }


    // account-list.xhtml
    public String getAccountTitle(Account account) {
        Locale locale = userController.getLocale();
        String bankAccountType = "";
        switch (account.getAccountType()) {
            case CASH:
                return messageSource.getMessage("account_accountType_cash", null, locale);
            case GIRO:
                bankAccountType = "giro";
            case SAVINGS:
                if (bankAccountType.equals("")) {
                    bankAccountType = "savings";
                }
                return StringUtils.isEmpty(account.getName()) ?
                        messageSource.getMessage("account_accountType_" + bankAccountType, null, locale)
                                + ": " + account.getIban() : account.getName();
            case CREDIT:
                return StringUtils.isEmpty(account.getName()) ?
                        messageSource.getMessage("account_accountType_credit", null, locale)
                                + ": " + account.getCreditCardNumber() : account.getName();
            default:
                return StringUtils.isEmpty(account.getName()) ?
                        "" : account.getName();
        }
    }

    public void selectAccount(Account account, int rowIndex) {
        id = account.getId();
        accountType = account.getAccountType();
        name = account.getName();
        institute = account.getInstitute();
        owner = account.getOwner();
        iban = account.getIban();
        creditCardNumber = account.getCreditCardNumber();
        expirationDate = account.getExpirationDate();
        balance = account.getBalance();
        linkedToAnotherAccount = account.getLinkedAccount() != null;
        linkedAccount = account.getLinkedAccount();
        editMode = true;
        editIndex = rowIndex;
    }

    public void deleteAccount(Account account) {
        accounts.remove(account);
        accountService.deleteAccount(account.getId());
        resetFields();
    }

    public boolean isCheckboxDisabled(Long id) {
        return !accountsChecked.get(id) && isLimitReached();
    }

    private Boolean isLimitReached() {
        return accountsChecked.values()
                .stream()
                .filter(checked -> checked)
                .count() == SHOWN_ACCOUNTS_LIMIT;
    }

    public boolean isBeingEdited(int rowIndex) {
        return rowIndex == editIndex;
    }


    // account-details.xhtml
    public List<Account> getShownAccounts() {
        return accounts.stream()
                .filter(account -> accountsChecked.get(account.getId()))
                .collect(Collectors.toList());
    }


    // Getters and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.iban = StringUtils.deleteWhitespace(iban);
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = StringUtils.deleteWhitespace(creditCardNumber);
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

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Map<Long, Boolean> getAccountsChecked() {
        return accountsChecked;
    }
}
