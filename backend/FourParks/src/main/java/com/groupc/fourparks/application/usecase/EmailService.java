package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.infraestructure.adapter.entity.EmailEntity;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(EmailEntity email) throws MessagingException;
}
