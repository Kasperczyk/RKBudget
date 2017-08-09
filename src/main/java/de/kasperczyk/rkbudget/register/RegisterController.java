package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import de.kasperczyk.rkbudget.user.User;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;

@Controller
@Scope("view")
@Join(path = "/register", to = "/pages/register.xhtml")
public class RegisterController implements Serializable {

    private final MessageSource messageSource;
    private final RegisterService registerService;

    private Language language;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

    private String token;
    private boolean registered;
    private boolean verified;
    private boolean expired;
    private boolean resent;

    @Autowired
    public RegisterController(MessageSource messageSource,
                              RegisterService registerService) {
        this.messageSource = messageSource;
        this.registerService = registerService;
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

    public String getPageTitle() {
        String pageTitle = messageSource.getMessage("register_title", null, getLocale());
        if (registered) {
            pageTitle += " - " + messageSource.getMessage("register_subtitle_registered", null, getLocale());
        }
        if (verified) {
            pageTitle += " - " + messageSource.getMessage("register_subtitle_verified", null, getLocale());
        }
        if (expired) {
            pageTitle = messageSource.getMessage("register_title_expired", null, getLocale());
        }
        if (resent) {
            pageTitle = messageSource.getMessage("register_title_resent", null, getLocale());
        }
        return pageTitle;
    }

    public void register() {
        Currency currency = registerService.getInitialCurrencyByIp(getIpAddress());
        boolean isStrongPassword = registerService.isStrongPassword(password);
        String securePassword = registerService.encodePassword(password);
        User user = new User(firstName, lastName, userName, email.toLowerCase(),
                securePassword, isStrongPassword, currency, getLocale());
        registered = registerService.register(user, getServerUrl(), getLocale());
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

    private String getServerUrl() {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        return url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
    }

    private void resetFields() {
        firstName = null;
        lastName = null;
        userName = null;
        email = null;
    }

    public void verify() {
        if (token != null) {
            expired = registerService.isExpired(token);
            if (!expired) {
                verified = registerService.verify(token);
            }
        }
    }

    public void resendVerificationMail() {
        registerService.resendVerificationEmail(token, getServerUrl(), getLocale());
        resent = true;
        expired = false;
        token = null;
    }

    public String navigateToLoginPage() {
        return "login?faces-redirect=true&includeViewParams=true";
    }

    public boolean noFlagSet() {
        return !(registered || verified || expired || resent);
    }

    public boolean isRegistered() {
        return registered;
    }

    public boolean isVerified() {
        return verified;
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

    public boolean isExpired() {
        return expired;
    }

    public boolean isResent() {
        return resent;
    }
}
