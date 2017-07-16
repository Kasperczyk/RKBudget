package de.kasperczyk.rkbudget.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    List<AccountType> getAllAccountTypes() {
        return Arrays.asList(AccountType.values());
    }

    AccountType getAccountTypeByName(String name) {
        return AccountType.valueOf(name);
    }

    void addAccount(Account account) {
        accountRepository.save(account);
    }

    List<Account> getLinkableAccounts() {
        return accountRepository.findAllByAccountType(AccountType.GIRO);
    }

    Account getAccountByIban(String iban) {
        return accountRepository.findByIban(iban);
    }

    boolean accountExists(Account account) {
        // todo improve example object
        return accountRepository.findAll(Example.of(account)).size() > 0;
    }
}