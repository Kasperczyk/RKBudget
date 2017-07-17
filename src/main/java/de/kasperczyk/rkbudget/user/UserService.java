package de.kasperczyk.rkbudget.user;

import de.kasperczyk.rkbudget.common.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new RuntimeException("wrong password");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    List<Language> getAllSupportedLanguages() {
        return Arrays.asList(Language.values());
    }

    List<Currency> getAllSupportedCurrencies() {
        return Arrays.asList(Currency.values());
    }

    Language getLanguageByName(String name) {
        return Language.valueOf(name);
    }

    Currency getCurrencyByName(String name) {
        return Currency.valueOf(name);
    }

    void updateUser(User user) {
        userRepository.save(user);
    }
}
