package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.usecase.EmailService;
import com.groupc.fourparks.infraestructure.model.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(final JavaMailSender mailSender, final TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(EmailDto email, String templateName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(email.to());
        mimeMessageHelper.setSubject(email.subject());

        Context context = new Context();
        context.setVariable("body", email.body());
        String html = templateEngine.process(templateName, context);
        mimeMessageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendEmailNewUser(EmailDto email) throws MessagingException {
        sendEmail(email, "new-user");
    }

    @Override
    public void sendEmailBlockedUser(EmailDto email) throws MessagingException {
        sendEmail(email, "blocked-user");
    }
}
