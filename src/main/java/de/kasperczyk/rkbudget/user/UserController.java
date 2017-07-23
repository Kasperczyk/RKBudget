package de.kasperczyk.rkbudget.user;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Locale;

@Controller
@Scope("session")
public class UserController {

    private final MessageSource messageSource;
    private final UserService userService;

    private User currentUser;
    private Language language;
    private Currency currency;

    @Autowired
    public UserController(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    public void initializeFields() {
        language = Language.valueOf(currentUser.getLocale().getDisplayName().toUpperCase());
        currency = currentUser.getCurrency();
    }

    public Locale getLocale() {
        return currentUser.getLocale();
    }

    public void save() {
        currentUser.setLocale(new Locale(language.getCountryCode()));
        currentUser.setCurrency(currency);
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
