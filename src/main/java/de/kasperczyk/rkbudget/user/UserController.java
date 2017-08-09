package de.kasperczyk.rkbudget.user;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@Scope("session")
@Join(path = "/profile", to = "/pages/settings.xhtml")
public class UserController {

    private final UserService userService;

    private User currentUser;

    // Personal Data
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String newPassword;

    // Preferences
    private Language language;
    private Currency currency;

    // UI Settings
    private String theme = "grey";
    private String menuColor = "layout-menu-dark";
    private boolean compactMode = true;
    private boolean orientationLTR = true;

    private boolean changingPassword;


    private Map<String, String> themeColors;
    private String menuLayout = "static";
    private String profileMode = "inline";


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        initThemeColors();
    }

    private void initThemeColors() {
        themeColors = new HashMap<>();
        themeColors.put("indigo", "#3F51B5");
        themeColors.put("blue", "#03A9F4");
        themeColors.put("blue-grey", "#607D8B");
        themeColors.put("brown", "#795548");
        themeColors.put("cyan", "#00bcd4");
        themeColors.put("green", "#4CAF50");
        themeColors.put("purple-amber", "#673AB7");
        themeColors.put("purple-cyan", "#673AB7");
        themeColors.put("teal", "#009688");
    }

    public void initializeFields() {
        initPersonalData();
        initPreferences();
        initUiSettings();
    }

    private void initPersonalData() {
        firstName = currentUser.getFirstName();
        lastName = currentUser.getLastName();
        userName = currentUser.getUserName();
        email = currentUser.getEmail();
    }

    private void initPreferences() {
        language = Language.valueBy(currentUser.getLocale().toLanguageTag());
        currency = currentUser.getCurrency();
    }

    private void initUiSettings() {
        theme = "grey";
        menuColor = "layout-menu-dark";
    }

    public void save() {
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setUserName(userName);
        currentUser.setEmail(email);
        currentUser.setLocale(new Locale(language.getCountryCode()));
        currentUser.setCurrency(currency);
        userService.updateUser(currentUser);
    }

    public void toggleChangingPasswordFlag() {
        changingPassword = !changingPassword;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(String menuColor) {
        this.menuColor = menuColor;
    }

    public boolean isChangingPassword() {
        return changingPassword;
    }

    public void setChangingPassword(boolean changingPassword) {
        this.changingPassword = changingPassword;
    }

    public Map<String, String> getThemeColors() {
        return themeColors;
    }

    public void setThemeColors(Map<String, String> themeColors) {
        this.themeColors = themeColors;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isCompactMode() {
        return compactMode;
    }

    public void setCompactMode(boolean compactMode) {
        this.compactMode = compactMode;
    }

    public String getMenuLayout() {
        if(this.menuLayout.equals("static"))
            return "menu-layout-static";
        else if(this.menuLayout.equals("overlay"))
            return "menu-layout-overlay";
        else if(this.menuLayout.equals("horizontal"))
            return "menu-layout-static menu-layout-horizontal";
        else
            return "menu-layout-static";
    }

    public void setMenuLayout(String menuLayout) {
        this.menuLayout = menuLayout;
    }

    public boolean isOrientationLTR() {
        return orientationLTR;
    }

    public void setOrientationLTR(boolean orientationLTR) {
        this.orientationLTR = orientationLTR;
    }

    public String getProfileMode() {
        return profileMode;
    }

    public void setProfileMode(String profileMode) {
        this.profileMode = profileMode;
    }

    public void setLightMenu() {
        this.menuColor = null;
    }

    public void setDarkMenu() {
        this.menuColor = "layout-menu-dark";
    }

    public Locale getLocale() {
        if (currentUser != null) {
            return currentUser.getLocale();
        } else {
            return FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
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
