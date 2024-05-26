package com.groupc.fourparks.application.service.PatternsHelpers;

import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.infraestructure.model.request.UserRegisterRequest;

public class ModifyUserDirector {
    ModifyUserConcreteBuilder modifyUserConcreteBuilder = new ModifyUserConcreteBuilder();
    public User make(User found,UserRegisterRequest received)
    {        
        return modifyUserConcreteBuilder.constructClone(received, found);        
    }
}
