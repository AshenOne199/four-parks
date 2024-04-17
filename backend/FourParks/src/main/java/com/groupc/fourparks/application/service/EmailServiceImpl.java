package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.usecase.EmailService;
import com.groupc.fourparks.infraestructure.adapter.entity.EmailEntity;
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

    @Override
    public void sendEmail(EmailEntity email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(email.to());
        mimeMessageHelper.setSubject(email.subject());

        Context context = new Context();
        context.setVariable("body", email.body());
        String html = templateEngine.process("email", context);
        mimeMessageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }
}
