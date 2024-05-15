package com.groupc.fourparks.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.application.mapper.ParkingSlotDtoMapper;
import com.groupc.fourparks.application.mapper.ParkingSlotRequestMapper;
import com.groupc.fourparks.application.usecase.ParkingSlotService;
import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.domain.port.ParkingSlotPort;
import com.groupc.fourparks.domain.port.SlotStatusPort;
import com.groupc.fourparks.domain.port.VehicleTypePort;
import com.groupc.fourparks.infraestructure.exception.InternalServerErrorException;
import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDto;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingSlotServiceImpl implements ParkingSlotService{
    
    private final ParkingSlotRequestMapper parkingSlotRequestMapper;

    private final ParkingSlotPort parkingSlotPort;
    private final ParkingPort parkingPort;
    private final VehicleTypePort vehicleTypePort;
    private final SlotStatusPort slotStatusPort;

    private final ParkingSlotDtoMapper parkingSlotDtoMapper;
    @Override
    public ParkingSlotDto newParkingSlot(ParkingSlotRequest parkingSlotRequest) {
        if(parkingSlotPort.getParkingSlotsByParking(parkingPort.findParkingByName(parkingSlotRequest.getParkingId().getName())).size() < Integer.parseInt(String.valueOf(parkingSlotRequest.getParkingId().getTotalSlots()))){
            var parkingSlotToCreate = parkingSlotRequestMapper.toDomain(parkingSlotRequest);
            var parkingToSave = parkingPort.findParkingByName(parkingSlotRequest.getParkingId().getName());
            var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingSlotRequest.getVehicleTypeId().getType());
            var SlotStatusToSave = slotStatusPort.findSlotStatusByStatus(parkingSlotRequest.getSlotStatusId().getStatus());
            return parkingSlotDtoMapper.toDto(parkingSlotPort.save(parkingSlotToCreate, SlotStatusToSave,parkingToSave, vehicleTypeToSave));
        }else{
            throw new InternalServerErrorException("Los espacios no pueden superar los espacios totales");
        }
    }

    @Override
    public ParkingSlotDto getParkingSlot(Long id) {
        ParkingSlot parkingSlot = parkingSlotPort.getParkingSlot(id);
        return parkingSlotDtoMapper.toDto(parkingSlot);
    }

    @Override
    public List<ParkingSlotDto> getParkingSlotsByParking(Long parkingId) {
        List<ParkingSlot> list = parkingSlotPort.getParkingSlotsByParking(parkingPort.findById(parkingId));
        
        List<ParkingSlotDto> finalList = new ArrayList<ParkingSlotDto>();
        for (ParkingSlot parkingSlot : list) {
            finalList.add(parkingSlotDtoMapper.toDto(parkingSlot));
        }
        return finalList;
    }

    @Override
    public ParkingSlotDto modifyParkingSlot(ParkingSlotRequest parkingSlotRequest) {
        ParkingSlot parkingSlotToModify = parkingSlotRequestMapper.toDomain(parkingSlotRequest);
        ParkingSlot parkingSlot = parkingSlotPort.getParkingSlot(parkingSlotRequest.getId());

        var parkingToSave = parkingPort.findParkingByName(parkingSlotRequest.getParkingId().getName());
        var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingSlotToModify.getVehicleTypeId().getType());
        var SlotStatusToSave = slotStatusPort.findSlotStatusByStatus(parkingSlotRequest.getSlotStatusId().getStatus());

        var parkingSlotModified = parkingSlotPort.save(parkingSlot,SlotStatusToSave, parkingToSave, vehicleTypeToSave);

        return parkingSlotDtoMapper.toDto(parkingSlotModified);
    }

    @Override
    public String deleteParkingSlot(Long id) {
        ParkingSlot parkingSlotToDelete = parkingSlotPort.getParkingSlot(id);
        parkingSlotPort.deleteParkingSlot(parkingSlotToDelete);
        return "Eliminacion correcta";   
    }
    
}
