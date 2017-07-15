package de.kasperczyk.rkbudget.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
