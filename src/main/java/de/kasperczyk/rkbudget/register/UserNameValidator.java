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

@Component
@Scope("request")
public class UserNameValidator implements Validator {

    private final MessageSource messageSource;
    private final RegisterController registerController;
    private final UserService userService;

    public UserNameValidator(MessageSource messageSource, RegisterController registerController, UserService userService) {
        this.messageSource = messageSource;
        this.registerController = registerController;
        this.userService = userService;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object userName) throws ValidatorException {
        if (userService.isUserNameTaken(userName.toString())) {
            Locale locale = registerController.getLocale();
            FacesMessage errorMessage = new FacesMessage(
                    messageSource.getMessage("register_error_userNameTaken", null, locale));
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessage);
        }
    }
}
