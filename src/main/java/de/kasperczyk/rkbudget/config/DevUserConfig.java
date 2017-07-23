package de.kasperczyk.rkbudget.config;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DevUserConfig {

    private final UserRepository userRepository;

    @Autowired
    public DevUserConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void configureDevUsers() {
        User devUserRene = createDevUser("Rene", "Kasperczyk", "Vyrwel",
                "kasperczyk.rene@gmail.com", "secret", new Locale("en"), Currency.EURO);
        User devUserChristina = createDevUser("Christina", "Meißner", "Ryana",
                "vyrwel@gmail.com", "geheim", new Locale("de"), Currency.EURO);
        userRepository.save(devUserRene);
        userRepository.save(devUserChristina);
    }

    private User createDevUser(String firstName, String lastName, String userName, String email,
                               String password, Locale locale, Currency currency) {
        User user = new User(firstName, lastName, userName, email, password);
        user.setLocale(locale);
        user.setCurrency(currency);
        user.setActivated(true);
        return user;
    }
}
