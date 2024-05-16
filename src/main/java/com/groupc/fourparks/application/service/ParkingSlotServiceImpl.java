package com.groupc.fourparks.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.application.mapper.ParkingSlotRequestMapper;
import com.groupc.fourparks.application.mapper.SlotToShowMapper;
import com.groupc.fourparks.application.usecase.ParkingSlotService;
import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.domain.model.SlotStatusEnum;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.domain.port.ParkingSlotPort;
import com.groupc.fourparks.domain.port.SlotStatusPort;
import com.groupc.fourparks.domain.port.VehicleTypePort;
import com.groupc.fourparks.infraestructure.exception.InternalServerErrorException;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;
import com.groupc.fourparks.infraestructure.model.request.SlotToShow;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingSlotServiceImpl implements ParkingSlotService{
    
    private final ParkingSlotRequestMapper parkingSlotRequestMapper;

    private final ParkingSlotPort parkingSlotPort;
    private final ParkingPort parkingPort;
    private final VehicleTypePort vehicleTypePort;
    private final SlotStatusPort slotStatusPort;
    
    private final SlotToShowMapper slotToShowMapper;
    @Override
    public SlotToShow newParkingSlot(ParkingSlotRequest parkingSlotRequest) {
        Parking parking = parkingPort.findById(parkingSlotRequest.getParkingId());
        if(parkingSlotPort.getParkingSlotsByParking(parkingPort.findParkingByName(parking.getName())).size() < Integer.parseInt(String.valueOf(parking.getTotal_slots()))){
            var parkingSlotToCreate = parkingSlotRequestMapper.toDomain(parkingSlotRequest);
            var parkingToSave = parkingPort.findParkingByName(parking.getName());
            var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingSlotRequest.getVehicleTypeId().getType());
            var SlotStatusToSave = slotStatusPort.findSlotStatusByStatus(parkingSlotRequest.getSlotStatusId().getStatus());
            return slotToShowMapper.toShow(parkingSlotPort.save(parkingSlotToCreate,SlotStatusToSave, parkingToSave, vehicleTypeToSave));
        }else{
            throw new InternalServerErrorException("Los espacios no pueden superar los espacios totales");
        }
    }

    @Override
    public SlotToShow getParkingSlot(Long id) {
        ParkingSlot parkingSlot = parkingSlotPort.getParkingSlot(id);
        return slotToShowMapper.toShow(parkingSlot);
    }

    @Override
    public List<SlotToShow> getParkingSlotsByParking(Long parkingId) {
        List<ParkingSlot> list = parkingSlotPort.getParkingSlotsByParking(parkingPort.findById(parkingId));
        
        List<SlotToShow> finalList = new ArrayList<SlotToShow>();
        for (ParkingSlot parkingSlot : list) {
            finalList.add(slotToShowMapper.toShow(parkingSlot));
        }
        return finalList;
    }

    @Override
    public SlotToShow modifyParkingSlot(ParkingSlotRequest parkingSlotRequest) {
        ParkingSlot parkingSlotToModify = parkingSlotRequestMapper.toDomain(parkingSlotRequest);
        ParkingSlot parkingSlot = parkingSlotPort.getParkingSlot(parkingSlotRequest.getId());
        Parking parking = parkingPort.findById(parkingSlotRequest.getParkingId());
        var parkingToSave = parkingPort.findParkingByName(parking.getName());
        var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingSlotToModify.getVehicleTypeId().getType());
        var SlotStatusToSave = slotStatusPort.findSlotStatusByStatus(parkingSlotRequest.getSlotStatusId().getStatus());
        if(parkingSlot.getSlotStatusId().getStatus() != parkingSlotRequest.getSlotStatusId().getStatus()){
            if(SlotStatusToSave.getStatus()==SlotStatusEnum.FULL){
                parkingToSave.setAvailable_slots(parkingToSave.getAvailable_slots()-1);
            }else{
                parkingToSave.setAvailable_slots(parkingToSave.getAvailable_slots()+1);
            }
            return slotToShowMapper.toShow(parkingSlotPort.save(parkingSlot,SlotStatusToSave, parkingToSave, vehicleTypeToSave));
        }else{
            return slotToShowMapper.toShow(parkingSlotPort.save(parkingSlot,SlotStatusToSave, parkingToSave, vehicleTypeToSave));
        }
    }

    @Override
    public String deleteParkingSlot(Long id) {
        ParkingSlot parkingSlotToDelete = parkingSlotPort.getParkingSlot(id);
        Parking parking = parkingPort.findById(parkingSlotToDelete.getParkingId().getId());
        if(parkingSlotToDelete.getSlotStatusId().getStatus()=="FULL"){
            parking.setAvailable_slots(parking.getAvailable_slots()+1);
            parkingPort.save(parking);
        }
        parkingSlotPort.deleteParkingSlot(parkingSlotToDelete);
        return "Eliminacion correcta";   
    }
    
}
