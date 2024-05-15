package com.groupc.fourparks.infraestructure.adapter;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.domain.model.VehicleType;
import com.groupc.fourparks.domain.port.VehicleTypePort;
import com.groupc.fourparks.infraestructure.adapter.mapper.VehicleTypeDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.VehicleTypeRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VehicleTypeJpaAdapter implements VehicleTypePort{
    private final VehicleTypeRepository vehicleTypeRepository;

    private final VehicleTypeDboMapper vehicleTypeDboMapper;

    @Override
    public VehicleType findVehicleTypeByType(String type) {
        var optionalVehicleType = vehicleTypeRepository.findVehicleTypeByType(type);

        if (optionalVehicleType.isEmpty()){
            throw new NotFoundException("VehicleType: " + type +" no registrado");
        }

        return vehicleTypeDboMapper.toDomain(optionalVehicleType.get());
    }
    
}
