package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.infraestructure.model.dto.EmailDto;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailNewUser(EmailDto email) throws MessagingException;
    void sendEmailBlockedUser(EmailDto email) throws MessagingException;
}
