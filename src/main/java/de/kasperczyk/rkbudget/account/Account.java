package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Account {

    @Id
//    @GenericGenerator(
//            name = "accountSequenceGenerator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "ACCOUNT_SEQUENCE"),
//                    @Parameter(name = "initial_value", value = "1"),
//                    @Parameter(name = "increment_size", value = "1")
//            }
//    )
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column
    private String name;

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

    public Account() {
        // for Hibernate
    }

    Account(AccountType accountType,
            String name,
            String institute,
            String owner,
            String iban,
            Date expirationDate,
            BigDecimal balance,
            User user) {
        this.accountType = accountType;
        this.name = name;
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
        if (!getName().equals(account.getName())) return false;
        if (!getInstitute().equals(account.getInstitute())) return false;
        if (getOwner() != null ? !getOwner().equals(account.getOwner()) : account.getOwner() != null) return false;
        if (!getIban().equals(account.getIban())) return false;
        if (!getExpirationDate().equals(account.getExpirationDate())) return false;
        if (!getBalance().equals(account.getBalance())) return false;
        return getUser() != null ? getUser().equals(account.getUser()) : account.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getAccountType() != null ? getAccountType().hashCode() : 0);
        result = 31 * result + getName().hashCode();
        result = 31 * result + getInstitute().hashCode();
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        result = 31 * result + getIban().hashCode();
        result = 31 * result + getExpirationDate().hashCode();
        result = 31 * result + getBalance().hashCode();
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
