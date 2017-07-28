package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.email.EmailService;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
public class RegisterService {

    private static final Logger LOG = Logger.getLogger(RegisterService.class);

    private final MessageSource messageSource;
    private final UserService userService;
    private final PasswordService passwordService;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public RegisterService(MessageSource messageSource,
                           UserService userService,
                           PasswordService passwordService,
                           EmailService emailService,
                           VerificationTokenRepository verificationTokenRepository) {
        this.messageSource = messageSource;
        this.userService = userService;
        this.passwordService = passwordService;
        this.emailService = emailService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    String saltAndHashPassword(String password) {
        return passwordService.saltAndHash(password);
    }

    boolean register(User user) {
        LOG.info("Trying to register user: " + user.toString());
        if (userService.exists(user)) {
            LOG.error("User already registered -> could not register user: " + user.toString());
            return false;
        } else {
            LOG.info("Registering user: " + user.toString());
            userService.create(user);
            VerificationToken verificationToken = createVerificationToken(user);
            sendVerificationEmail(user, verificationToken);
            return true;
        }
    }

    private VerificationToken createVerificationToken(User user) {
        LOG.info("Creating verification token for user " + user.toString());
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        LOG.info("Saving verification token: " + verificationToken.toString() + " to the database");
        return verificationTokenRepository.save(verificationToken);
    }

    private void sendVerificationEmail(User user, VerificationToken verificationToken) {
        Locale locale = new Locale("en");
        String subject = messageSource.getMessage("entry_mail_registrationSubject", null, locale);
        String text = messageSource.getMessage("entry_mail_registrationText", null, locale);;
        String confirmationUrl = "localhost:8080/pages/entry/verify.xhtml?token=" + verificationToken.getToken();
        text += "\n\n" + confirmationUrl;
        emailService.sendVerificationEmail(user.getEmail(), subject, text);
    }

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
