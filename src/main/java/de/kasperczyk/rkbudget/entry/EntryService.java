package de.kasperczyk.rkbudget.entry;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.springframework.stereotype.Service;

@Service
class EntryService {

    private final UserService userService;

    EntryService(UserService userService) {
        this.userService = userService;
    }

    boolean registerUser(User user) {
        if (userService.exists(user)) {
            return false;
        } else {
            userService.addUser(user);
            return true;
        }
    }
}
