package de.kasperczyk.rkbudget.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("session")
public class UserController {

    private final UserService userService;

    private User currentUser;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

        // todo
        currentUser = userService.login("kasperczyk.rene@gmail.com", "geheim");
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
