package de.kasperczyk.rkbudget.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@Scope("session")
public class UserController {

    private final Environment environment;
    private final MessageSource messageSource;
    private final UserService userService;

    private User currentUser;
    private Language language;

    @Autowired
    public UserController(Environment environment, MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.environment = environment;
        this.userService = userService;

        if (Arrays.stream(environment.getActiveProfiles())
                .anyMatch(env -> env.equals("dev-h2") || env.equals("dev-postgres"))) {
            currentUser = userService.login("kasperczyk.rene@gmail.com", "geheim");
            FacesContext.getCurrentInstance().getViewRoot().setLocale(getLocale());
        }
    }

    public Locale getLocale() {
        return currentUser.getLocale();
    }

    public List<Language> getAllSupportedLanguages() {
        return userService.getAllSupportedLanguages();
    }

    public String getLanguageName(Language language) {
        return messageSource.getMessage(language.getKey(), null, getLocale());
    }

    public void save() {
        currentUser.setLocale(new Locale(language.getCountryCode()));
        userService.updateUser(currentUser);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
