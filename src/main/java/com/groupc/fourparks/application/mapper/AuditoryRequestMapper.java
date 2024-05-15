package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.Auditory;

import com.groupc.fourparks.infraestructure.model.request.AuditoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditoryRequestMapper {
    Auditory toDomain(AuditoryRequest auditoryRequest);
}
