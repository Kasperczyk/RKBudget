package de.kasperczyk.rkbudget.user;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = Logger.getLogger(UserService.class);

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

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean isUserNameTaken(String userName) {
        return userRepository.findByUserName(userName) != null;
    }

    public void create(User user) {
        LOG.info("Saving user: " + user.toString() + " to the database");
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
