package de.kasperczyk.rkbudget.entry;

import de.kasperczyk.rkbudget.email.EmailService;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
class EntryService {

    private final MessageSource messageSource;

    EntryService(UserService userService,
                 VerificationTokenRepository verificationTokenRepository,
                 MessageSource messageSource,
                 EmailService emailService) {
        this.userService = userService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.messageSource = messageSource;
        this.emailService = emailService;
    }

    User loginUser(String emailOrUserName, String password) throws Exception {
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



















    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    boolean registerUser(User user) {
        if (userService.exists(user)) {
            return false;
        } else {
            userService.addUser(user);
            VerificationToken verificationToken = createVerificationToken(user);
            sendVerificationEmail(user, verificationToken);
            return true;
        }
    }

    private VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        return verificationTokenRepository.save(verificationToken);
    }

    private void sendVerificationEmail(User user, VerificationToken verificationToken) {
        Locale locale = new Locale("en");
        String subject = messageSource.getMessage("entry_mail_registrationSubject", null, locale);
        String text = messageSource.getMessage("entry_mail_registrationText", null, locale);;
        // todo
        String confirmationUrl = "localhost:8080/pages/entry/verification.xhtml?token=" + verificationToken.getToken();
        text += "\n\n" + confirmationUrl;
        emailService.sendVerificationEmail(user.getEmail(), subject, text);
    }

    // todo
    boolean verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new RuntimeException("invalid token");
        } else {
//            if (new Date().after(verificationToken.getExpiryDate())) {
//                throw new RuntimeException("token expired");
//            } else {
                 userService.activateUser(verificationToken.getUser().getId());
                 verificationTokenRepository.delete(verificationToken);
                 return true;
//            }
        }
    }
}
