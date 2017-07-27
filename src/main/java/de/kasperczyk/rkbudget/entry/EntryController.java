package de.kasperczyk.rkbudget.entry;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserController;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.util.Locale;

@Component
@Scope("request")
@Join(path = "/login", to = "/pages/entry/login.xhtml")
public class EntryController {

    private Language language;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private Currency currency;

    private boolean registered;
    private boolean submitted;

    public EntryController(EntryService entryService,
                           UserController userController) {
        this.entryService = entryService;
        this.userController = userController;
    }

    public Locale getLocale() {
        if (language != null) {
            return new Locale(language.getCountryCode());
        } else {
            Locale requestLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
            language = Language.valueBy(requestLocale.getLanguage());
            return requestLocale;
        }
    }

    public void register() {
        User user = new User(firstName, lastName, userName, email, password, currency, getLocale());
        registered = entryService.registerUser(user);
        submitted = true;
        if (registered) {
            resetFields();
        }
    }


    private final EntryService entryService;
    private final UserController userController;

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/pages/entry/login?faces-redirect=true";
    }


    private String emailOrUserName;


    private String token;
    private boolean verified;

    private void resetFields() {
        firstName = null;
        lastName = null;
        userName = null;
        email = null;
        password = null;
    }

    public void verifyUser() {
        verified = entryService.verifyUser(token);
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRegistered() {
        return registered;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getEmailOrUserName() {
        return emailOrUserName;
    }

    public void setEmailOrUserName(String emailOrUserName) {
        this.emailOrUserName = emailOrUserName;
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
