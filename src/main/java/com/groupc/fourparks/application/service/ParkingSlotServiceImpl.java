package com.groupc.fourparks.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.application.mapper.ParkingSlotRequestMapper;
import com.groupc.fourparks.application.usecase.ParkingSlotService;
import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.domain.model.SlotStatusEnum;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.domain.port.ParkingSlotPort;
import com.groupc.fourparks.domain.port.SlotStatusPort;
import com.groupc.fourparks.domain.port.VehicleTypePort;
import com.groupc.fourparks.infraestructure.exception.InternalServerErrorException;
import com.groupc.fourparks.infraestructure.model.dto.ParkingDto;
import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDto;
import com.groupc.fourparks.infraestructure.model.dto.SlotStatusDto;
import com.groupc.fourparks.infraestructure.model.dto.VehicleTypeDto;
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

    @Override
    public ParkingSlotDto newParkingSlot(ParkingSlotRequest parkingSlotRequest) {
        Parking parking = parkingPort.findById(parkingSlotRequest.getParkingId());
        if(parkingSlotPort.getParkingSlotsByParking(parkingPort.findParkingByName(parking.getName())).size() < Integer.parseInt(String.valueOf(parking.getTotalSlots()))){
            var parkingSlotToCreate = parkingSlotRequestMapper.toDomain(parkingSlotRequest);
            var parkingToSave = parkingPort.findParkingByName(parking.getName());
            var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingSlotRequest.getVehicleTypeId().getType());
            var SlotStatusToSave = slotStatusPort.findSlotStatusByStatus(parkingSlotRequest.getSlotStatusId().getStatus());
            return this.parkingSlotToAddListConvert(parkingSlotPort.save(parkingSlotToCreate,SlotStatusToSave, parkingToSave, vehicleTypeToSave));
        }else{
            throw new InternalServerErrorException("Los espacios no pueden superar los espacios totales");
        }
    }

    @Override
    public ParkingSlotDto getParkingSlot(Long id) {
        ParkingSlot parkingSlot = parkingSlotPort.getParkingSlot(id);
        return this.parkingSlotToAddListConvert(parkingSlot);
    }

    @Override
    public List<ParkingSlotDto> getParkingSlotsByParking(Long parkingId) {
        List<ParkingSlot> list = parkingSlotPort.getParkingSlotsByParking(parkingPort.findById(parkingId));
        
        List<ParkingSlotDto> finalList = new ArrayList<>();
        for (ParkingSlot parkingSlot : list) {
            finalList.add(this.parkingSlotToAddListConvert(parkingSlot));
        }
        return finalList;
    }

    @Override
    public ParkingSlotDto modifyParkingSlot(ParkingSlotRequest parkingSlotRequest) {
        ParkingSlot parkingSlotToModify = parkingSlotRequestMapper.toDomain(parkingSlotRequest);
        ParkingSlot parkingSlot = parkingSlotPort.getParkingSlot(parkingSlotRequest.getId());
        Parking parking = parkingPort.findById(parkingSlotRequest.getParkingId());
        var parkingToSave = parkingPort.findParkingByName(parking.getName());
        var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingSlotToModify.getVehicleTypeId().getType());
        var SlotStatusToSave = slotStatusPort.findSlotStatusByStatus(parkingSlotRequest.getSlotStatusId().getStatus());
        if(!Objects.equals(parkingSlot.getSlotStatusId().getStatus(), parkingSlotRequest.getSlotStatusId().getStatus())){
            if(SlotStatusToSave.getStatus()==SlotStatusEnum.FULL){
                parkingToSave.setAvailableSlots(parkingToSave.getAvailableSlots()-1);
            }else{
                parkingToSave.setAvailableSlots(parkingToSave.getAvailableSlots()+1);
            }
        }
        return this.parkingSlotToAddListConvert(parkingSlotPort.save(parkingSlot,SlotStatusToSave, parkingToSave, vehicleTypeToSave));
    }

    @Override
    public String deleteParkingSlot(Long id) {
        ParkingSlot parkingSlotToDelete = parkingSlotPort.getParkingSlot(id);
        Parking parking = parkingPort.findById(parkingSlotToDelete.getParkingId().getId());
        if(Objects.equals(parkingSlotToDelete.getSlotStatusId().getStatus(), "FULL")){
            parking.setAvailableSlots(parking.getAvailableSlots()+1);
            parkingPort.save(parking);
        }
        parkingSlotPort.deleteParkingSlot(parkingSlotToDelete);
        return "Eliminacion correcta";   
    }

    private ParkingSlotDto parkingSlotToAddListConvert(ParkingSlot parkingSlot) {
        ParkingSlotDto parkingSlotToAddList = new ParkingSlotDto();
        ParkingDto parkingToAddList = new ParkingDto();
        SlotStatusDto statusToAddList = new SlotStatusDto();
        VehicleTypeDto vehicleTypeToAddList = new VehicleTypeDto();
        parkingToAddList.setId(parkingSlot.getParkingId().getId());
        parkingToAddList.setName(parkingSlot.getParkingId().getName());
        vehicleTypeToAddList.setId(parkingSlot.getVehicleTypeId().getId());
        vehicleTypeToAddList.setType(parkingSlot.getVehicleTypeId().getType());
        statusToAddList.setId(parkingSlot.getSlotStatusId().getId());
        statusToAddList.setStatus(parkingSlot.getSlotStatusId().getStatus());
        parkingSlotToAddList.setId(parkingSlot.getId());
        parkingSlotToAddList.setParkingId(parkingToAddList);
        parkingSlotToAddList.setVehicleTypeId(vehicleTypeToAddList);
        parkingSlotToAddList.setSlotStatusId(statusToAddList);

        return parkingSlotToAddList;
    }

    
}
