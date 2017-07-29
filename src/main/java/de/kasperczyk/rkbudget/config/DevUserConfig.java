package de.kasperczyk.rkbudget.config;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DevUserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DevUserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void configureDevUsers() {

        User devUserRene = createDevUser("Rene", "Kasperczyk", "Vyrwel",
                "kasperczyk.rene@gmail.com", passwordEncoder.encode("secret"),
                new Locale("en"), Currency.EURO);
        User devUserChristina = createDevUser("Christina", "Meißner", "Ryana",
                "vyrwel@gmail.com", passwordEncoder.encode("geheim"),
                new Locale("de"), Currency.EURO);
        userRepository.save(devUserRene);
        userRepository.save(devUserChristina);
    }

    private User createDevUser(String firstName, String lastName, String userName, String email,
                               String password, Locale locale, Currency currency) {
        User user = new User(firstName, lastName, userName, email, password, currency, locale);
        user.setActivated(true);
        return user;
    }
}
