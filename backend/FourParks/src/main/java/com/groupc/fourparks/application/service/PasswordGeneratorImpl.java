package com.groupc.fourparks.application.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGeneratorImpl {

    public String generateRandomPassword() {
        final String capital = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String minus = "abcdefghijklmnopqrstuvwxyz";
        final String nums = "123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        int totalLength = 8;

        while (sb.length() < totalLength) {
            int randomIndex = random.nextInt(3);
            switch (randomIndex) {
                case 0:
                    sb.append(capital.charAt(random.nextInt(capital.length())));
                    break;
                case 1:
                    sb.append(minus.charAt(random.nextInt(minus.length())));
                    break;
                case 2:
                    sb.append(nums.charAt(random.nextInt(nums.length())));
                    break;
            }
        }

        // Si la longitud supera el máximo, truncamos el exceso
        if (sb.length() > totalLength) {
            sb.setLength(totalLength);
        }

        return sb.toString();
    }
}
