package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import de.kasperczyk.rkbudget.user.User;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import java.util.Locale;

@Controller
@Scope("request")
@Join(path = "/register", to = "/pages/register.xhtml")
public class RegisterController {

    private final RegisterService registerService;

    private Language language;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

    private String token;
    private boolean verified;

    private boolean registered;
    private boolean submitted;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    public void register() {
        // todo currency based on location
        Currency defaultCurrency = Currency.EURO;
        String securePassword = registerService.saltAndHashPassword(password);
        User user = new User(firstName, lastName, userName, email, securePassword, defaultCurrency, getLocale());
        registered = registerService.register(user);
        submitted = true;
        if (registered) {
            resetFields();
        }
    }

    private String saltAndHash(String password) {


        return null;
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

    private void resetFields() {
        firstName = null;
        lastName = null;
        userName = null;
        email = null;
        password = null;
    }

    public void verifyUser() {
        verified = registerService.verifyUser(token);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

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
}
