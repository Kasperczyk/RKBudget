package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.user.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("request")
public class EmailValidator implements Validator {

    private static final String EMAIL_PATTERN =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                    "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\" +
                    "\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0" +
                    "-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[" +
                    "0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|" +
                    "[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x" +
                    "0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private final MessageSource messageSource;
    private final RegisterController registerController;
    private final UserService userService;
    private final Pattern pattern;

    public EmailValidator(MessageSource messageSource,
                          RegisterController registerController,
                          UserService userService) {
        this.messageSource = messageSource;
        this.registerController = registerController;
        this.userService = userService;
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object email) throws ValidatorException {
        Locale locale = registerController.getLocale();
        validateEmailValidity(email, locale);
        validateEmailAvailability(email, locale);
    }

    private void validateEmailValidity(Object email, Locale locale) {
        Matcher matcher = pattern.matcher(email.toString());
        if (!matcher.matches()) {
            FacesMessage errorMessage = new FacesMessage(
                    messageSource.getMessage("register_error_invalidEmail", null, locale));
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessage);
        }
    }

    private void validateEmailAvailability(Object email, Locale locale) {
        if (userService.emailExists(email.toString())) {
            FacesMessage errorMessage = new FacesMessage(
                    messageSource.getMessage("register_error_emailTaken", null, locale));
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessage);
        }
    }
}
