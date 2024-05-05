package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.domain.port.ParkingSlotPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingSlotDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ParkingSlotRepository;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingSlotJpaAdapter implements ParkingSlotPort{
    final private ParkingSlotRepository parkingSlotRepository;

    final private ParkingSlotDboMapper parkingSlotDboMapper;

    final private ParkingDboMapper parkingDboMapper;

    @Override
    public void save(ParkingSlot parkingSlot, Parking parking) {
        var parkingSlotToSave = parkingSlotDboMapper.toDbo(parkingSlot);
        var parkingToSave = parkingDboMapper.toDbo(parking);
        parkingSlotToSave.setParkingId(parkingToSave);
        parkingSlotRepository.save(parkingSlotToSave);
    }
}
