package de.kasperczyk.rkbudget.language;

import de.kasperczyk.rkbudget.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Locale;

@Controller
@Scope("request")
public class LanguageController {

    private final MessageSource messageSource;
    private final LanguageService languageService;
    private final UserController userController;

    @Autowired
    public LanguageController(MessageSource messageSource,
                              LanguageService languageService,
                              UserController userController) {
        this.messageSource = messageSource;
        this.languageService = languageService;
        this.userController = userController;
    }

    public List<Language> getAllSupportedLanguages() {
        return languageService.getAllSupportedLanguages();
    }

    public String getLanguageName(Language language) {
        Locale locale;
        if (userController.getCurrentUser() != null) {
            locale = userController.getCurrentUser().getLocale();
        } else {
            locale = new Locale(language.getCountryCode());
        }
        return messageSource.getMessage(language.getMessageKey(), null, locale);
    }
}
