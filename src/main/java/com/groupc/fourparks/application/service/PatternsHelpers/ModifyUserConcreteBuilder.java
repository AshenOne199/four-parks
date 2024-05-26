package com.groupc.fourparks.application.service.PatternsHelpers;

import java.time.LocalDate;
import com.groupc.fourparks.domain.model.CreditCard;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;

public class ModifyUserConcreteBuilder {

    public User constructClone(UserRegisterRequest received, User found) 
    { 
        
        User returnable = found;
        
        
        returnable.setFirstLastname(received.getFirstLastname());
        returnable.setFirstName(received.getFirstName());
        returnable.setSecondLastname(received.getSecondLastname());
        returnable.setSecondName(received.getSecondName());
        returnable.setUpdatedAt(LocalDate.now());    
        
        
        
        return returnable;
    }
    
}
