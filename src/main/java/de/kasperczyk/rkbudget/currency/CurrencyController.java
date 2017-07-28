package de.kasperczyk.rkbudget.currency;

import de.kasperczyk.rkbudget.register.RegisterController;
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
    private final RegisterController registerController;

    @Autowired
    public CurrencyController(MessageSource messageSource,
                              CurrencyService currencyService,
                              UserController userController,
                              RegisterController registerController) {
        this.messageSource = messageSource;
        this.currencyService = currencyService;
        this.userController = userController;
        this.registerController = registerController;
    }

    public List<Currency> getAllSupportedCurrencies() {
        return currencyService.getAllSupportedCurrencies();
    }

    public String getCurrencyName(Currency currency) {
        Locale locale;
        if (userController.getCurrentUser() != null) {
            locale = userController.getCurrentUser().getLocale();
        } else if (registerController.getLanguage() != null) {
            locale = new Locale(registerController.getLanguage().getCountryCode());
        } else {
            locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        return messageSource.getMessage(currency.getMessageKey(), null, locale);
    }

}
