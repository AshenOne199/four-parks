package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.Auditory;

import com.groupc.fourparks.infraestructure.model.dto.AuditoryDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditoryDtoMapper {
    AuditoryDto toDto(Auditory auditory);
}


