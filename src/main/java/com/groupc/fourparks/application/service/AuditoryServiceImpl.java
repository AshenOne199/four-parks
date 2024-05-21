package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.mapper.AuditoryDtoMapper;
import com.groupc.fourparks.application.mapper.AuditoryRequestMapper;
import com.groupc.fourparks.application.mapper.UserDtoMapper;
import com.groupc.fourparks.application.usecase.AuditoryService;
import com.groupc.fourparks.application.usecase.ManagerService;
import com.groupc.fourparks.domain.model.Activity;
import com.groupc.fourparks.domain.port.AuditoryPort;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.model.dto.ActivityDto;
import com.groupc.fourparks.infraestructure.model.dto.AuditoryDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import com.groupc.fourparks.infraestructure.model.request.ActivityRequest;
import com.groupc.fourparks.infraestructure.model.request.AuditoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AuditoryServiceImpl implements AuditoryService {

    private final AuditoryPort auditoryPort;
    private final AuditoryRequestMapper auditoryRequestMapper;
    private final AuditoryDtoMapper auditoryDtoMapper;

    private final UserPort userPort;

    private final UserDboMapper userDboMapper;
    private final UserDtoMapper userDtoMapper;

    

    @Override
    public List<UserDto> usersRegisteredOnDate(Date beginning, Date ending) {
        return List.of();
    }

    @Override
    public List<ActivityDto> modificationsOnDate(Date beginning, Date ending) {
        return List.of();
    }

    @Override
    public Activity RegisterActivity(ActivityRequest ActivityRequest) {
        return null;
    }

    @Override
    public AuditoryDto RegisterActivity(AuditoryRequest auditoryRequest) {
        var auditory = auditoryRequestMapper.toDomain(auditoryRequest);
        return auditoryDtoMapper.toDto(auditoryPort.save(auditory));
    }

    @Override
    public List<UserDto> usersOnDate(Date beginning, Date ending) {
        
        List<UserDto> returnable = new ArrayList<>();
        List<UserEntity> receiver = new ArrayList<>();

        receiver = userPort.findAll();

        for(UserEntity user : receiver)
        {
            if(user.getCreatedAt().after(beginning) && user.getCreatedAt().before(ending))
            {
                returnable.add(userDtoMapper.toDto(userDboMapper.toDomain(user)));
            }
        }

        return returnable;
    }
}
