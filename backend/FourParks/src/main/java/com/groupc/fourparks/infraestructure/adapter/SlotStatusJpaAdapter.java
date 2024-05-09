package com.groupc.fourparks.infraestructure.adapter;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.domain.model.SlotStatus;
import com.groupc.fourparks.domain.port.SlotStatusPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.SlotStatusDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.SlotStatusRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SlotStatusJpaAdapter implements SlotStatusPort{

    private final SlotStatusRepository slotStatusRepository;

    private final SlotStatusDboMapper slotStatusDboMapper;

    public SlotStatus findSlotStatusByStatus(String status) {
        var optionalStatus = slotStatusRepository.findByStatus(status);

        if (optionalStatus.isEmpty()){
            throw new NotFoundException("Estatus: " + status +" no registrado");
        }

        return slotStatusDboMapper.toDomain(optionalStatus.get());
    }
    
}
