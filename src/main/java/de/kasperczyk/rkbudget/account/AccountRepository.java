package de.kasperczyk.rkbudget.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAllByAccountType(AccountType accountType);

    Account findByIban(String iban);
}
