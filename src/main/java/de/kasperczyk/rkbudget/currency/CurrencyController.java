package de.kasperczyk.rkbudget.currency;

import de.kasperczyk.rkbudget.entry.EntryController;
import de.kasperczyk.rkbudget.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Locale;

@Controller
@Scope("request")
public class CurrencyController {

    private final MessageSource messageSource;
    private final CurrencyService currencyService;
    private final UserController userController;
    private final EntryController entryController;

    @Autowired
    public CurrencyController(MessageSource messageSource,
                              CurrencyService currencyService,
                              UserController userController,
                              EntryController entryController) {
        this.messageSource = messageSource;
        this.currencyService = currencyService;
        this.userController = userController;
        this.entryController = entryController;
    }

    public List<Currency> getAllSupportedCurrencies() {
        return currencyService.getAllSupportedCurrencies();
    }

    public String getCurrencyName(Currency currency) {
        Locale locale;
        if (userController.getCurrentUser() != null) {
            locale = userController.getCurrentUser().getLocale();
        } else if (entryController.getLanguage() != null) {
            locale = new Locale(entryController.getLanguage().getCountryCode());
        } else {
            locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        return messageSource.getMessage(currency.getMessageKey(), null, locale);
    }

}
