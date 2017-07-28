package de.kasperczyk.rkbudget.email;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOG = Logger.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String emailAddress, String subject, String text) {
        LOG.info("Sending verification email to '" + emailAddress + "'");
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailAddress);
        email.setSubject(subject);
        email.setText(text);
        sendEmail(email);
    }

    private void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }
}
