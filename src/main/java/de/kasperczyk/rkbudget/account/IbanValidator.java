package de.kasperczyk.rkbudget.account;

import de.kasperczyk.rkbudget.user.UserController;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@Component
@Scope("request")
public class IbanValidator implements Validator {

    private final MessageSource messageSource;
    private final UserController userController;

    @Autowired
    public IbanValidator(MessageSource messageSource, UserController userController) {
        this.messageSource = messageSource;
        this.userController = userController;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!isIbanValid(value)) {
            FacesMessage errorMessage = new FacesMessage(
                    messageSource.getMessage("account_error_invalidIban", null, userController.getLocale()));
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean isIbanValid(Object value) {
        String iban = StringUtils.deleteWhitespace(value.toString());
        return new IBANCheckDigit().isValid(iban);
    }
}
