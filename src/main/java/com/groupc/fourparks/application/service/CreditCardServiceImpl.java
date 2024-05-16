package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.usecase.CreditCardService;
import com.groupc.fourparks.infraestructure.model.dto.CreditCardDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Override
    public boolean validateCreditCard(CreditCardDto creditCardDto) {
        if (!isValidExpiryDate(creditCardDto.getExpirationDate())) {
            return false;
        }

        if (!isValidCVV(creditCardDto.getCardNumber(), creditCardDto.getCvv())) {
            return false;
        }

        return isValidCardNumber(creditCardDto.getCardNumber());
    }

    private static boolean isValidExpiryDate(String expiryDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
            Date expDate = sdf.parse(expiryDate);
            Date currentDate = new Date();
            return expDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean isValidCVV(String cardNumber, String cvv) {
        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            return cvv.length() == 4;
        } else {
            return cvv.length() == 3;
        }
    }

    private static boolean isValidCardNumber(String cardNumber) {
        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            if (cardNumber.length() < 15 || cardNumber.length() > 19) {
                return false;
            }
        } else {
            if (cardNumber.length() < 16 || cardNumber.length() > 19) {
                return false;
            }
        }
        return isLuhnValid(cardNumber);
    }

    private static boolean isLuhnValid(String cardNo) {
        int nDigits = cardNo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNo.charAt(i) - '0';

            if (isSecond)
                d = d * 2;

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}
