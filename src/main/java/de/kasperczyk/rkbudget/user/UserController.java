package de.kasperczyk.rkbudget.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.Locale;

@Controller
@Scope("session")
public class UserController {

    private final Environment environment;
    private final UserService userService;

    private User currentUser;

    @Autowired
    public UserController(Environment environment, UserService userService) {
        this.environment = environment;
        this.userService = userService;

        if (Arrays.stream(environment.getActiveProfiles())
                .anyMatch(env -> env.equals("dev-h2") || env.equals("dev-postgres"))) {
            currentUser = userService.login("kasperczyk.rene@gmail.com", "geheim");
            FacesContext.getCurrentInstance().getViewRoot().setLocale(getLocale());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Locale getLocale() {
        return currentUser.getLocale();
    }
}
