package com.groupc.fourparks.infraestructure.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.domain.model.Activity;
import com.groupc.fourparks.domain.port.ActivityPort;
import com.groupc.fourparks.infraestructure.adapter.entity.ActivityEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.ActivityDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ActivityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivityJpaAdapter implements ActivityPort{

    private final ActivityRepository activityRepository;
    private final ActivityDboMapper activityDboMapper;
    @Override
    public List<Activity> read() 
    {
        List<ActivityEntity> receiver = new ArrayList<>();
        List<Activity> returnable = new ArrayList<>();
        receiver = activityRepository.findAll();
        for(ActivityEntity activityEntity: receiver)
        {
            returnable.add(activityDboMapper.toDomain(activityEntity));
        }
        return returnable;
    }
    
}
