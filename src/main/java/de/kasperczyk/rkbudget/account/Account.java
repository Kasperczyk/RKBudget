package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private AccountType accountType;

    @Column
    private String institute;

    @Column
    private String owner;

    @Column
    private String iban;

    @Column
    private Date expirationDate;

    @Column
    private BigDecimal balance;

    @ManyToOne
    private User user;

    public Account(AccountType accountType,
                   String institute,
                   String owner,
                   String iban,
                   Date expirationDate,
                   BigDecimal balance,
                   User user) {
        this.accountType = accountType;
        this.institute = institute;
        this.owner = owner;
        this.iban = iban;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (getAccountType() != account.getAccountType()) return false;
        if (!getInstitute().equals(account.getInstitute())) return false;
        if (!getOwner().equals(account.getOwner())) return false;
        if (!getIban().equals(account.getIban())) return false;
        if (!getExpirationDate().equals(account.getExpirationDate())) return false;
        return getBalance().equals(account.getBalance());
    }

    @Override
    public int hashCode() {
        int result = getAccountType().hashCode();
        result = 31 * result + getInstitute().hashCode();
        result = 31 * result + getOwner().hashCode();
        result = 31 * result + getIban().hashCode();
        result = 31 * result + getExpirationDate().hashCode();
        result = 31 * result + getBalance().hashCode();
        return result;
    }
}
