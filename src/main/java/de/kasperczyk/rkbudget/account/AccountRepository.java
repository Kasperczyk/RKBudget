package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUser(User user);

    List<Account> findAllByAccountType(AccountType accountType);

    Account findByIban(String iban);
}
