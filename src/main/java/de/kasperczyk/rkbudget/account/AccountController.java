package de.kasperczyk.rkbudget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Scope("session")
public class AccountController {

    private static final int SHOWN_ACCOUNTS_LIMIT = 3; // todo extend in the future, maybe allow the user to choose from several layouts

    private final MessageSource messageSource;
    private final AccountService accountService;

    private List<Account> accounts;
    private AccountType accountType;
    private String institute;
    private String owner;
    private String iban;
    private Date expirationDate; // todo Java 8 LocalDate
    private BigDecimal balance;
    private Map<Long, Boolean> accountsChecked;

    @Autowired
    public AccountController(MessageSource messageSource, AccountService accountService) {
        this.messageSource = messageSource;
        this.accountService = accountService;
        accounts = new ArrayList<>();
        accountsChecked = new HashMap<>();
    }

    public void addAccount() {
        Account account = new Account(accountType, institute, owner, iban, expirationDate, balance);
        accountService.addAccount(account);
        accountsChecked.put(account.getId(), !isLimitReached());
        accounts.add(account);
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

    public List<Account> getShownAccounts() {
        return accounts.stream()
                .filter(account -> accountsChecked.get(account.getId()))
                .collect(Collectors.toList());
    }

    public String getAccountTypeName(AccountType accountType) {
        return messageSource.getMessage(accountType.getKey(), null, new Locale("de"));
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<AccountType> getAccountTypes() {
        return accountService.getAccountTypes();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    public Map<Long, Boolean> getAccountsChecked() {
        return accountsChecked;
    }
}
