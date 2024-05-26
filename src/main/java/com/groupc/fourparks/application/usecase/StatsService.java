package com.groupc.fourparks.application.usecase;
import java.util.Date;
import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.ReservationDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;



public interface StatsService {
    
    
      String incomesOnDate(Date from, Date from2, Long id);
      String reservationsOnDate(Date from, Date from2, Long id);
      String vehicleType(Date from, Date from2, Long id, Long idTypeVehicle);
      List<UserDto> getUsersForParking(Date from, Date from2, Long id);
      

      
    

}
