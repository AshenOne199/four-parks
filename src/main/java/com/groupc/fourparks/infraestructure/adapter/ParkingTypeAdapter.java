package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.infraestructure.exception.NotFoundException;
import org.springframework.stereotype.Service;

import com.groupc.fourparks.domain.model.ParkingType;
import com.groupc.fourparks.domain.port.ParkingTypePort;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingTypeDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ParkingTypeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ParkingTypeAdapter implements ParkingTypePort{

    final private ParkingTypeRepository parkingTypeRepository;
    final private ParkingTypeDboMapper parkingTypeDboMapper;

    @Override
    public ParkingType findParkingTypeByType(String type) {
        var parkingType = parkingTypeRepository.findParkingTypeByType(type);
        if (parkingType.isEmpty()) {
            throw new NotFoundException("El tipo de parking no es valido");
        }
        return parkingTypeDboMapper.toDomain(parkingType.get()) ;
    }
}
