package com.groupc.fourparks.infraestructure.adapter.mapper;



import com.groupc.fourparks.domain.model.Activity;

import com.groupc.fourparks.infraestructure.adapter.entity.ActivityEntity;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityDboMapper {
    ActivityEntity toDbo(Activity activity);

    @InheritInverseConfiguration
    Activity toDomain(ActivityEntity activityEntity);
}
