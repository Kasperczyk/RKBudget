package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    @Autowired
    AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    List<AccountType> getAllAccountTypes() {
        return Arrays.asList(AccountType.values());
    }

    AccountType getAccountTypeByName(String name) {
        return AccountType.valueOf(name);
    }

    List<Account> getLinkableAccounts() {
        return accountRepository.findAllByAccountType(AccountType.GIRO);
    }

    Account getAccountByIban(String iban) {
        return accountRepository.findByIban(iban);
    }

    void updateAccount(Account updatedAccount, Long id) {
        Account account = accountRepository.findOne(id);
        account.setName(updatedAccount.getName());
        account.setInstitute(updatedAccount.getInstitute());
        account.setOwner(updatedAccount.getOwner());
        account.setIban(updatedAccount.getIban());
        account.setCreditCardNumber(updatedAccount.getCreditCardNumber());
        account.setLinkedAccount(updatedAccount.getLinkedAccount());
        account.setExpirationDate(updatedAccount.getExpirationDate());
        account.setBalance(updatedAccount.getBalance());
        accountRepository.save(account);
    }

    boolean accountExists(Account account) {
        // todo improve example object
        return accountRepository.findAll(Example.of(account)).size() > 0;
    }

    void saveAccount(Account account) {
        accountRepository.save(account);
    }

    void deleteAccount(Long id) {
        Account account = accountRepository.findOne(id);
        accountRepository.delete(account);
    }

    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    List<Account> getAllAccountsFor(User user) {
        return accountRepository.findAllByUser(user);
    }
}