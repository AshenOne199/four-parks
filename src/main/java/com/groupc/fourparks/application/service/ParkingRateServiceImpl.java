package com.groupc.fourparks.application.service;

import java.util.ArrayList;
import java.util.List;

import com.groupc.fourparks.infraestructure.exception.BadRequestException;
import org.springframework.stereotype.Service;

import com.groupc.fourparks.application.mapper.ParkingRateRequestMapper;
import com.groupc.fourparks.application.mapper.RateToShowMapper;
import com.groupc.fourparks.application.usecase.ParkingRateService;
import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.domain.port.ParkingRatePort;
import com.groupc.fourparks.domain.port.VehicleTypePort;
import com.groupc.fourparks.infraestructure.model.request.ParkingRateRequest;
import com.groupc.fourparks.infraestructure.model.request.RateToShow;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingRateServiceImpl implements ParkingRateService{
    private final ParkingRateRequestMapper parkingRateRequestMapper;

    private final ParkingRatePort parkingRatePort;
    private final ParkingPort parkingPort;
    private final VehicleTypePort vehicleTypePort;

    private final RateToShowMapper rateToShowMapper;
    @Override
    public RateToShow newParkingRate(ParkingRateRequest parkingRateRequest) {
        ParkingRate parkingRateToSave;
        var parkingRateToCreate = parkingRateRequestMapper.toDomain(parkingRateRequest);
        var parkingToSave = parkingPort.findById(parkingRateRequest.getParkingId());
        var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingRateRequest.getVehicleTypeId().getType());
        try{
            parkingRateToSave = parkingRatePort.save(parkingRateToCreate, parkingToSave,vehicleTypeToSave);
        }catch(Exception e){
            throw new BadRequestException("Ya existe un precio para ese tipo de vehiculo");
        }
        
        return rateToShowMapper.toShow(parkingRateToSave); 
    }
    @Override
    public RateToShow getParkingRate(Long id) {
        ParkingRate parkingSlot = parkingRatePort.getParkingRate(id);
        return rateToShowMapper.toShow(parkingSlot);
    }
    @Override
    public List<RateToShow> getParkingRatesByParking(Long parkingId) {
        List<ParkingRate> list = parkingRatePort.getParkingRatesByParking(parkingPort.findById(parkingId));
        
        List<RateToShow> finalList = new ArrayList<>();
        for (ParkingRate parkingRate : list) {
            finalList.add(rateToShowMapper.toShow(parkingRate));
        }
        return finalList;
    }
    @Override
    public RateToShow modifyParkingRate(ParkingRateRequest parkingRateRequest) {
        ParkingRate parkingRateModified;
        ParkingRate parkingRateToModify = parkingRateRequestMapper.toDomain(parkingRateRequest);
        ParkingRate parkingRate = parkingRatePort.getParkingRate(parkingRateRequest.getId());
        var parkingToSave = parkingPort.findById(parkingRateRequest.getParkingId());
        var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingRateToModify.getVehicleTypeId().getType());
        parkingRate.setRate(parkingRateRequest.getRate());
        try{
            parkingRateModified = parkingRatePort.save(parkingRate,parkingToSave, vehicleTypeToSave);
        }catch(Exception e){
            throw new BadRequestException("Ya existe un precio para ese tipo de vehiculo");
        }
        return rateToShowMapper.toShow(parkingRateModified);
    }
    @Override
    public String deleteParkingRate(Long id) {
        ParkingRate parkingRateToDelete = parkingRatePort.getParkingRate(id);
        parkingRatePort.deleteParkingRate(parkingRateToDelete);
        return "Eliminacion correcta"; 
    }
    
}
