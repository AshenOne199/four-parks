package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.domain.model.SlotStatus;
import com.groupc.fourparks.domain.model.VehicleType;
import com.groupc.fourparks.domain.port.ParkingSlotPort;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingSlotEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingSlotDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.SlotStatusDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.VehicleTypeDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ParkingSlotRepository;
import com.groupc.fourparks.infraestructure.adapter.repository.ParkingSlotRepositoryCustom;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDetailsDto;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingSlotJpaAdapter implements ParkingSlotPort{
    final private ParkingSlotRepository parkingSlotRepository;
    final private ParkingSlotRepositoryCustom parkingSlotRepositoryCustom;

    final private ParkingSlotDboMapper parkingSlotDboMapper;
    final private VehicleTypeDboMapper vehicleTypeDboMapper;
    final private SlotStatusDboMapper slotStatusDboMapper;

    final private ParkingDboMapper parkingDboMapper;

    @Override
    public ParkingSlot save(ParkingSlot parkingSlot,SlotStatus slotStatus,Parking parking, VehicleType vehicleType) {
        var parkingSlotToSave = parkingSlotDboMapper.toDbo(parkingSlot);
        var slotStatusToSave = slotStatusDboMapper.toDbo(slotStatus);
        var vehicleTypeToSave = vehicleTypeDboMapper.toDbo(vehicleType);
        var parkingToSave = parkingDboMapper.toDbo(parking);
        
        parkingSlotToSave.setParkingId(parkingToSave);
        parkingSlotToSave.setVehicleTypeId(vehicleTypeToSave);
        parkingSlotToSave.setSlotStatusId(slotStatusToSave);

        return parkingSlotDboMapper.toDomain(parkingSlotRepository.save(parkingSlotToSave)) ;
    }

    @Override
    public ParkingSlot save(ParkingSlot parkingSlot) {
        var parkingSlotToSave = parkingSlotDboMapper.toDbo(parkingSlot);
        var parkingSlotSaved = parkingSlotRepository.save(parkingSlotToSave);
        return parkingSlotDboMapper.toDomain(parkingSlotSaved);
    }

    @Override
    public ParkingSlot getParkingSlot(Long id) {
         var optionalParkingSlot = parkingSlotRepository.findById(id);

        if (optionalParkingSlot.isEmpty()){
            throw new NotFoundException("id: " + id +" no registrado");
        }

        return parkingSlotDboMapper.toDomain(optionalParkingSlot.get());
    }

    @Override
    public List<ParkingSlot> getParkingSlotsByParking(Parking parking) {
        List<ParkingSlot> allParkingSlots = new ArrayList<>();
        List<ParkingSlotEntity> parkingSlotsReceiver = parkingSlotRepository.findByParkingId(parkingDboMapper.toDbo(parking));
        for (ParkingSlotEntity parkingSlotEntity : parkingSlotsReceiver) {
            allParkingSlots.add(parkingSlotDboMapper.toDomain(parkingSlotEntity));
        }
        return allParkingSlots;
    }

    @Override
    public void deleteParkingSlot(ParkingSlot parkingSlot) {
        var parkingSlotToDelete = parkingSlotDboMapper.toDbo(parkingSlot);
        parkingSlotRepository.delete(parkingSlotToDelete);
    }

    @Override
    public List<ParkingSlotDetailsDto> findEmptySlotsByParkingId(Long parkingId) {
        return parkingSlotRepositoryCustom.findEmptySlotsByParkingId(parkingId);
    }
}
