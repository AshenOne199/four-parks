package com.groupc.fourparks.infraestructure.adapter.mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.groupc.fourparks.domain.model.SlotStatus;
import com.groupc.fourparks.infraestructure.adapter.entity.SlotStatusEntity;

@Mapper(componentModel = "spring")
public interface SlotStatusDboMapper {
    SlotStatusEntity toDbo(SlotStatus slotStatus);

    @InheritInverseConfiguration
    SlotStatus toDomain(SlotStatusEntity slotStatusEntity);
}
