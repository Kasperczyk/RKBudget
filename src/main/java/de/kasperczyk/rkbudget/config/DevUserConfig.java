package de.kasperczyk.rkbudget.config;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevUserConfig {

    private final UserRepository userRepository;

    @Autowired
    public DevUserConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void configureDevUser() {
        User devUser = new User();
        devUser.setFirstName("Rene");
        devUser.setLastName("Kasperczyk");
        devUser.setUserName("Rene Kasperczyk");
        devUser.setEmail("kasperczyk.rene@gmail.com");
        devUser.setPassword("geheim");

        userRepository.save(devUser);
    }
}
