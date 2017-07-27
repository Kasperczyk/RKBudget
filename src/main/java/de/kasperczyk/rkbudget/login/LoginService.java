package de.kasperczyk.rkbudget.login;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
class LoginService {

    private final MessageSource messageSource;
    private final UserService userService;

    @Autowired
    LoginService(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    User login(String emailOrUserName, String password) throws Exception {
        User user = userService.getUserByEmailAddressOrUserName(emailOrUserName);
        String errorMessage = validateUser(user, emailOrUserName, password);
        if (isBlank(errorMessage)) {
            return user;
        } else {
            throw new Exception(errorMessage);
        }
    }

    private String validateUser(User user, String emailOrUserName, String password) {
        String errorMessage = "";
        Locale locale = new Locale("en"); // todo
        String[] args = {emailOrUserName};
        if (user == null) {
            errorMessage = messageSource.getMessage("entry_error_userNotFound", args, locale);
        } else if (!user.getPassword().equals(password)) {
            errorMessage = messageSource.getMessage("entry_error_wrongPassword", args, locale);
        } else if (!user.isActivated()) {
            errorMessage = messageSource.getMessage("entry_error_notActivated", args, locale);
        }
        return errorMessage;
    }
}
