package de.kasperczyk.rkbudget.email;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

    private static final Logger LOG = Logger.getLogger(EmailService.class);

    private final MessageSource messageSource;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(MessageSource messageSource, JavaMailSender mailSender) {
        this.messageSource = messageSource;
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String confirmationUrl, Locale locale) {
        LOG.info("Sending verification email to '" + to + "'");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(messageSource.getMessage("register_email_subject", null, locale));
            helper.setText(messageSource.getMessage("register_email_text",
                    new String[]{confirmationUrl}, locale), true);
        } catch (MessagingException e) {
            // todo
        }
        sendMimeMessage(mimeMessage);
    }

    private void sendMimeMessage(MimeMessage mimeMessage) {
        mailSender.send(mimeMessage);
    }
}
