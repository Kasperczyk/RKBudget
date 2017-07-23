package de.kasperczyk.rkbudget.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

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
    private Currency currency;

    @Autowired
    public UserController(Environment environment, MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.environment = environment;
        this.userService = userService;
    }

    public void initializeFields() {
        language = Language.valueOf(currentUser.getLocale().getDisplayName().toUpperCase());
        currency = currentUser.getCurrency();
    }

    public Locale getLocale() {
        return currentUser.getLocale();
    }

    public List<Language> getAllSupportedLanguages() {
        return userService.getAllSupportedLanguages();
    }

    public List<Currency> getAllSupportedCurrencies() {
        return userService.getAllSupportedCurrencies();
    }

    public String getLanguageName(Language language) {
        return messageSource.getMessage(language.getMessageKey(), null, getLocale());
    }

    public String getCurrencyName(Currency currency) {
        return messageSource.getMessage(currency.getMessageKey(), null, getLocale());
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
