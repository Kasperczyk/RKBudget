package de.kasperczyk.rkbudget.login;

import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
class LoginService {

    private static final Logger LOG = Logger.getLogger(LoginService.class);

    private final MessageSource messageSource;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    LoginService(MessageSource messageSource, UserService userService, PasswordEncoder passwordEncoder) {
        this.messageSource = messageSource;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    User login(String emailOrUserName, String password) throws Exception {
        LOG.info("Trying to log in user with email or user name: '" + emailOrUserName + "'");
        User user = userService.getUserByEmailAddressOrUserName(emailOrUserName);
        String errorMessage = validateUser(user, emailOrUserName, password);
        if (isBlank(errorMessage)) {
            LOG.info("Successfully logged in user: " + user.toString());
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
            LOG.error("User with email or user name '" + emailOrUserName + "' not found");
        } else if (!passwordEncoder.matches(password, user.getPassword())) {
            errorMessage = messageSource.getMessage("entry_error_wrongPassword", args, locale);
            LOG.error("Incorrect password provided for user with email or user name '" + emailOrUserName + "'");
        } else if (!user.isActivated()) {
            errorMessage = messageSource.getMessage("entry_error_notActivated", args, locale);
            LOG.error("User with email or user name '" + emailOrUserName + "' has not been activated yet");
        }
        return errorMessage;
    }
}
