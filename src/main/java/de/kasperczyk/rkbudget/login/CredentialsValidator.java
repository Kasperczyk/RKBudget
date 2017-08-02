package de.kasperczyk.rkbudget.login;

import de.kasperczyk.rkbudget.register.RegisterController;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Locale;

@Component
@Scope("request")
public class CredentialsValidator implements Validator {

    private final MessageSource messageSource;
    private final RegisterController registerController;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CredentialsValidator(MessageSource messageSource,
                                RegisterController registerController,
                                UserService userService,
                                PasswordEncoder passwordEncoder) {
        this.messageSource = messageSource;
        this.registerController = registerController;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        UIInput emailOrUserNameInput = (UIInput) component;
        emailOrUserNameInput.setValid(true);
        String emailOrUserName = value.toString();
        UIInput passwordInput = (UIInput) context.getViewRoot().findComponent("loginForm:password");
        passwordInput.setValid(true);
        String password = passwordInput.getSubmittedValue() != null ?
                passwordInput.getSubmittedValue().toString() : "";

        if (!validateEmailOrUserName(emailOrUserName)) {
            handleException(context, emailOrUserNameInput, "login_error_userNotFound");
        } else {
            if (!StringUtils.isBlank(password)) {
                User user = userService.getUserByEmailAddressOrUserName(emailOrUserName);
                if (!user.isActivated()) {
                    handleException(context, emailOrUserNameInput, "login_error_notActivated");
                } else if (!passwordEncoder.matches(password, user.getPassword())) {
                    handleException(context, passwordInput, "login_error_wrongPassword");
                }
            }
        }
    }

    private boolean validateEmailOrUserName(String emailOrUserName) {
        return userService.emailExists(emailOrUserName) || userService.userNameExists(emailOrUserName);
    }

    private void handleException(FacesContext context, UIInput input, String messageKey) {
        Locale locale = registerController.getLocale();
        FacesMessage errorMessage = new FacesMessage(messageSource.getMessage(messageKey, null, locale));
        errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage(input.getClientId(), errorMessage);
        input.setValid(false);
    }
}
