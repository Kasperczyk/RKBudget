package de.kasperczyk.rkbudget.register;

import de.kasperczyk.rkbudget.currency.Currency;
import de.kasperczyk.rkbudget.email.EmailService;
import de.kasperczyk.rkbudget.user.User;
import de.kasperczyk.rkbudget.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class RegisterService {

    private static final Logger LOG = Logger.getLogger(RegisterService.class);

    private final UserService userService;
    private final EmailService emailService;
    private final LocationService locationService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserService userService,
                           EmailService emailService,
                           LocationService locationService,
                           VerificationTokenRepository verificationTokenRepository,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.locationService = locationService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    Currency getInitialCurrencyByIp(String ip) {
        return Currency.valueBy(locationService.getCountryCodeByIp(ip));
    }

    boolean isStrongPassword(String password) {
        return userService.isStrongPassword(password);
    }

    boolean register(User user, String serverUrl, Locale locale) {
        LOG.info("Registering user: " + user.toString());
        userService.create(user);
        VerificationToken verificationToken = createVerificationToken(user);
        sendVerificationEmail(user, verificationToken, serverUrl, locale);
        return true;
    }

    private VerificationToken createVerificationToken(User user) {
        LOG.info("Creating verification token for user " + user.toString());
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        LOG.info("Saving verification token: " + verificationToken.toString() + " to the database");
        return verificationTokenRepository.save(verificationToken);
    }

    private void sendVerificationEmail(User user, VerificationToken verificationToken, String serverUrl, Locale locale) {
        String confirmationUrl = serverUrl + "/register?token=" + verificationToken.getToken();
        emailService.sendVerificationEmail(user.getEmail(), confirmationUrl, locale);
    }

    void resendVerificationEmail(String token, String serverUrl, Locale locale) {
        VerificationToken oldToken = verificationTokenRepository.findByToken(token);
        User user = oldToken.getUser();
        verificationTokenRepository.delete(oldToken.getId());
        VerificationToken verificationToken = createVerificationToken(user);
        sendVerificationEmail(user, verificationToken, serverUrl, locale);
    }

    boolean isExpired(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        return verificationToken != null && verificationToken.getExpiryDate().before(new Date());
    }

    boolean verify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new RuntimeException("invalid token");
        } else {
            if (new Date().after(verificationToken.getExpiryDate())) {
                throw new RuntimeException("token expired");
            } else {
                userService.activateUser(verificationToken.getUser().getId());
                verificationTokenRepository.delete(verificationToken);
                return true;
            }
        }
    }
}
