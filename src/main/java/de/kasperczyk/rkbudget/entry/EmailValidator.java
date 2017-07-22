package de.kasperczyk.rkbudget.entry;

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
            "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    private final MessageSource messageSource;
    private final Pattern pattern;
    // todo locale

    public EmailValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object email) throws ValidatorException {
        Matcher matcher = pattern.matcher(email.toString());
        if (!matcher.matches()) {
            FacesMessage errorMessage = new FacesMessage(
                    messageSource.getMessage("entry_error_invalidEmailAddress", null, new Locale("en")));
            throw new ValidatorException(errorMessage);
        }
    }
}
