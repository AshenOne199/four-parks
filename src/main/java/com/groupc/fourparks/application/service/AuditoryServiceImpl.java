package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.mapper.AuditoryDtoMapper;

import com.groupc.fourparks.application.mapper.UserDtoMapper;
import com.groupc.fourparks.application.usecase.AuditoryService;

import com.groupc.fourparks.domain.model.Activity;
import com.groupc.fourparks.domain.model.Auditory;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.ActivityPort;
import com.groupc.fourparks.domain.port.AuditoryPort;
import com.groupc.fourparks.domain.port.UserPort;

import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;

import com.groupc.fourparks.infraestructure.model.dto.AuditoryDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuditoryServiceImpl implements AuditoryService {

    private final AuditoryPort auditoryPort;

    private final AuditoryDtoMapper auditoryDtoMapper;

    private final UserPort userPort;
    private final ActivityPort activityPort;

    private final UserDboMapper userDboMapper;
    private final UserDtoMapper userDtoMapper;

    @Override
    public List<UserDto> usersOnDate(Date beginning, Date ending) {
        List<UserDto> returnable = new ArrayList<>();
        List<UserEntity> receiver = userPort.findAll();

        for (UserEntity user : receiver) {
            if (user.getCreatedAt().after(beginning) && user.getCreatedAt().before(ending)) {
                returnable.add(userDtoMapper.toDto(userDboMapper.toDomain(user)));
            }
        }

        return returnable;
    }

    @Override
    public AuditoryDto registerAuditory(Long activityId, Long userId) {
        Auditory auditory = new Auditory();
        List<Activity> allActivities = activityPort.read();
        for (Activity activity : allActivities) {
            if (Objects.equals(activity.getId(), activityId)) {
                auditory.setActivity(activity.getId());

            }
        }
        List<User> allUsers = userPort.findAllUsers();
        for (User user : allUsers) {
            if (Objects.equals(user.getId(), userId)) {
                auditory.setUser(user.getId());
                auditory.setIp(user.getIp());

            }
        }
        
        return auditoryDtoMapper.toDto(auditoryPort.registerAuditory(auditory));
    }

    @Override
    public List<AuditoryDto> getAuditories(Date beginning, Date ending, Long userId) {
        List<AuditoryDto> returnable = new ArrayList<>();
        List<Auditory> receiver = auditoryPort.read();
        for (Auditory auditory : receiver) {
            if (userId == -1L) {
                if (auditory.getHappening_date().after(beginning) && auditory.getHappening_date().before(ending)) {
                    AuditoryDto auditoryDto = auditoryDtoMapper.toDto(auditory);  
                    auditoryDto.setActivity(auditory.getActivity());               
                    auditoryDto.setUser(auditory.getUser());
                    returnable.add(auditoryDto);
                }
            } else {
                if (auditory.getHappening_date().after(beginning) && auditory.getHappening_date().before(ending) && Objects.equals(auditory.getUser(), userId)) {
                    AuditoryDto auditoryDto = auditoryDtoMapper.toDto(auditory);         
                    auditoryDto.setActivity(auditory.getActivity());               
                    auditoryDto.setUser(auditory.getUser());          
                    returnable.add(auditoryDto);
                }
            }

        }
        return returnable;
    }
}
