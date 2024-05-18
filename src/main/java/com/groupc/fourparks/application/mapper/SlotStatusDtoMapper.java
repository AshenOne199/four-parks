package com.groupc.fourparks.application.mapper;

import com.groupc.fourparks.domain.model.SlotStatus;
import com.groupc.fourparks.infraestructure.model.dto.SlotStatusDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SlotStatusDtoMapper {
    SlotStatusDto toDto(SlotStatus slotStatus);
    SlotStatus toDomain(SlotStatusDto slotStatusDto);
}
