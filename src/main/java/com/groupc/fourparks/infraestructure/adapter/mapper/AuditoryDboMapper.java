package com.groupc.fourparks.infraestructure.adapter.mapper;

import com.groupc.fourparks.domain.model.Auditory;



import com.groupc.fourparks.infraestructure.adapter.entity.AuditoryEntity;



import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditoryDboMapper {



    @InheritInverseConfiguration
    Auditory toDomain(AuditoryEntity userEntity);
    AuditoryEntity toDbo(Auditory domain);


}













