package com.groupc.fourparks.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.application.mapper.ParkingRateDtoMapper;
import com.groupc.fourparks.application.mapper.ParkingRateRequestMapper;
import com.groupc.fourparks.application.usecase.ParkingRateService;
import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.domain.port.ParkingRatePort;
import com.groupc.fourparks.domain.port.VehicleTypePort;
import com.groupc.fourparks.infraestructure.model.dto.ParkingRateDto;
import com.groupc.fourparks.infraestructure.model.request.ParkingRateRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingRateServiceImpl implements ParkingRateService{
    private final ParkingRateRequestMapper parkingRateRequestMapper;

    private final ParkingRatePort parkingRatePort;
    private final ParkingPort parkingPort;
    private final VehicleTypePort vehicleTypePort;

    private final ParkingRateDtoMapper parkingRateDtoMapper;
    @Override
    public ParkingRateDto newParkingRate(ParkingRateRequest parkingRateRequest) {
        var parkingRateToCreate = parkingRateRequestMapper.toDomain(parkingRateRequest);
        var parkingToSave = parkingPort.findParkingByName(parkingRateRequest.getParkingId().getName());
        var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingRateRequest.getVehicleTypeId().getType());
        return parkingRateDtoMapper.toDto(parkingRatePort.save(parkingRateToCreate, parkingToSave,vehicleTypeToSave));
    }
    @Override
    public ParkingRateDto getParkingRate(Long id) {
        ParkingRate parkingSlot = parkingRatePort.getParkingRate(id);
        return parkingRateDtoMapper.toDto(parkingSlot);
    }
    @Override
    public List<ParkingRateDto> getParkingRatesByParking(Long parkingId) {
        List<ParkingRate> list = parkingRatePort.getParkingRatesByParking(parkingPort.findById(parkingId));
        
        List<ParkingRateDto> finalList = new ArrayList<ParkingRateDto>();
        for (ParkingRate parkingRate : list) {
            finalList.add(parkingRateDtoMapper.toDto(parkingRate));
        }
        return finalList;
    }
    @Override
    public ParkingRateDto modifyParkingRate(ParkingRateRequest parkingRateRequest) {
        ParkingRate parkingRateToModify = parkingRateRequestMapper.toDomain(parkingRateRequest);
        ParkingRate parkingRate = parkingRatePort.getParkingRate(parkingRateRequest.getId());

        var parkingToSave = parkingPort.findParkingByName(parkingRateRequest.getParkingId().getName());
        var vehicleTypeToSave = vehicleTypePort.findVehicleTypeByType(parkingRateToModify.getVehicleTypeId().getType());
        parkingRate.setRate(Float.parseFloat(parkingRateRequest.getRate()));
        var parkingRateModified = parkingRatePort.save(parkingRate,parkingToSave, vehicleTypeToSave);

        return parkingRateDtoMapper.toDto(parkingRateModified);
    }
    @Override
    public String deleteParkingRate(Long id) {
        ParkingRate parkingRateToDelete = parkingRatePort.getParkingRate(id);
        parkingRatePort.deleteParkingRate(parkingRateToDelete);
        return "Eliminacion correcta"; 
    }
    
}
