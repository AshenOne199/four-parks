package com.groupc.fourparks.application.usecase;

import com.groupc.fourparks.domain.model.Activity;
import com.groupc.fourparks.infraestructure.model.dto.ActivityDto;
import com.groupc.fourparks.infraestructure.model.dto.AuditoryDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import com.groupc.fourparks.infraestructure.model.request.ActivityRequest;
import com.groupc.fourparks.infraestructure.model.request.AuditoryRequest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AuditoryService {
    List<UserDto> usersRegisteredOnDate (Date beginning, Date ending);
    List<ActivityDto> modificationsOnDate  (Date beginning, Date ending);


    public Activity RegisterActivity(ActivityRequest ActivityRequest);
    AuditoryDto RegisterActivity(AuditoryRequest auditoryRequest);

    List<UserDto> usersOnDate(Date localDate, Date localDate2);


    //4 How many reservations were done on dates
    //5 4 but for specific user
    //6 4 but for specific parking
    //7 How many times user got blocked
    //8 How many times manager crud parking


}
