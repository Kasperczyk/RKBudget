package de.kasperczyk.rkbudget.user;

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

    public boolean exists(User user) {
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    public void addUser(User user) {
        userRepository.save(user);
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

    void updateUser(User updatedUser) {
        User user = userRepository.findOne(updatedUser.getId());
        user.setLocale(updatedUser.getLocale());
        user.setCurrency(updatedUser.getCurrency());
        userRepository.save(user);
    }
}
