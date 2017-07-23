package de.kasperczyk.rkbudget.user;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    public User getUserByEmailAddressOrUserName(String emailOrUserName) {
        User user;
        boolean isEmailAddress = EmailValidator.getInstance().isValid(emailOrUserName);
        if (isEmailAddress) {
            user = userRepository.findByEmail(emailOrUserName);
        } else {
            user = userRepository.findByUserName(emailOrUserName);
        }
        return user;
    }











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

    public void activateUser(Long id) {
        User user = userRepository.findOne(id);
        user.setActivated(true);
        userRepository.save(user);
    }
}
