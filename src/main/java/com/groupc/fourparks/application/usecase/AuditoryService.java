package com.groupc.fourparks.application.usecase;
import com.groupc.fourparks.infraestructure.model.dto.AuditoryDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import java.util.Date;
import java.util.List;

public interface AuditoryService {
    
    
    
    public AuditoryDto registerAuditory( Long activityId,Long userId);
    List<UserDto> usersOnDate(Date localDate, Date localDate2);

     List<AuditoryDto> getAuditories(Date from, Date from2, Long userId);
    


    

}
