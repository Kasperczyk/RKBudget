package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import de.kasperczyk.rkbudget.user.User;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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
        Currency currency = registerService.getInitialCurrencyByIp(getIpAddress());
        String securePassword = registerService.encodePassword(password);
        User user = new User(firstName, lastName, userName, email, securePassword, currency, getLocale());
        registered = registerService.register(user);
        submitted = true;
        if (registered) {
            resetFields();
        }
    }

    private String getIpAddress() {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
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
    }

    public void verify() {
        if (token != null) {
            verified = registerService.verify(token);
        }
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public boolean isRegistered() {
        return registered;
    }

    public boolean isVerified() {
        return verified;
    }
}
