package de.kasperczyk.rkbudget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class AccountController {

    private final MessageSource messageSource;
    private final AccountService accountService;

    private List<Account> accounts;
    private AccountType accountType;
    private String institute;
    private String owner;
    private String iban;
    private Date expirationDate; // todo Java 8 LocalDate
    private BigDecimal balance;

    @Autowired
    public AccountController(MessageSource messageSource, AccountService accountService) {
        this.messageSource = messageSource;
        this.accountService = accountService;
        accounts = new ArrayList<>();
    }

    public void addAccount() {
        Account account = new Account(accountType, institute, owner, iban, expirationDate, balance);
        accountService.addAccount(account);
        accounts.add(account);
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
}
