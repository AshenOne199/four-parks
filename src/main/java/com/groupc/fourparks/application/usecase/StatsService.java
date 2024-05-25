package com.groupc.fourparks.application.usecase;
import java.util.Date;
import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.UserDto;



public interface StatsService {
    
    
      String incomesOnDate(Date from, Date from2, Long id);

      List<UserDto> getUsersForParking(Date from, Date from2, Long id);
    

}
