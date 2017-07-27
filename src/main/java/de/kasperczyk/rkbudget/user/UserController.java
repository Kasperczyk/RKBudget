package de.kasperczyk.rkbudget.user;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.language.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@Scope("session")
public class UserController {

    private Map<String, String> themeColors;
    private String theme = "grey";
    private boolean compact = true;
    private String menuLayout = "static";
    private boolean orientationLTR = true;
    private String menuClass = null;
    private String profileMode = "inline";

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

    public boolean isCompact() {
        return compact;
    }

    public void setCompact(boolean compact) {
        this.compact = compact;
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

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass;
    }

    public String getProfileMode() {
        return profileMode;
    }

    public void setProfileMode(String profileMode) {
        this.profileMode = profileMode;
    }

    public void setLightMenu() {
        this.menuClass = null;
    }

    public void setDarkMenu() {
        this.menuClass = "layout-menu-dark";
    }

    private final MessageSource messageSource;
    private final UserService userService;

    private User currentUser;
    private Language language;
    private Currency currency;

    @Autowired
    public UserController(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;

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
