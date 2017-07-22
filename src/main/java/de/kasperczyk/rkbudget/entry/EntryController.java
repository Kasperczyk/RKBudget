package de.kasperczyk.rkbudget.entry;

import de.kasperczyk.rkbudget.user.Currency;
import de.kasperczyk.rkbudget.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Scope("request")
public class EntryController {

    private final EntryService entryService;

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private boolean registered;
    private boolean submitted;
    private String token;
    private boolean verified;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    public void registerUser() {
        User user = new User(firstName, lastName, userName, email, password);
        user.setCurrency(Currency.EURO); // todo
        user.setLocale(Locale.ENGLISH); // todo
        registered = entryService.registerUser(user);
        submitted = true;
        resetFields();
    }

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
}
