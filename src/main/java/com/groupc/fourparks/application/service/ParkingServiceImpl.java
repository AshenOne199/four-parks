package com.groupc.fourparks.application.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.groupc.fourparks.application.mapper.NewParkingRequestMapper;
import com.groupc.fourparks.application.mapper.ParkingDtoMapper;
import com.groupc.fourparks.application.mapper.SetAdminToParkingRequestMapper;
import com.groupc.fourparks.application.usecase.ParkingService;
import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.port.CityPort;
import com.groupc.fourparks.domain.port.LocationPort;
import com.groupc.fourparks.domain.port.OpeningHoursPort;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.domain.port.ParkingTypePort;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.exception.InternalServerErrorException;
import com.groupc.fourparks.infraestructure.model.dto.ParkingDto;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;
import com.groupc.fourparks.infraestructure.model.request.SetAdminToParkingRequest;
import com.groupc.fourparks.infraestructure.model.request.SlotStatusRequest;
import com.groupc.fourparks.infraestructure.model.request.VehicleTypeRequest;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingServiceImpl implements ParkingService{
    private final NewParkingRequestMapper newParkingRequestMapper;
    private final SetAdminToParkingRequestMapper setAdminToParkingRequestMapper;

    private final ParkingPort parkingPort;
    private final LocationPort locationPort;
    private final OpeningHoursPort openingHoursPort;
    private final CityPort cityPort;
    private final ParkingTypePort parkingTypePort;
    private final UserPort userPort;

    private final ParkingDtoMapper parkingDtoMapper;

    private final ParkingSlotServiceImpl parkingSlotServiceImpl;

    @Transactional
    public ParkingDto newParking(NewParkingRequest newParkingRequest) {
        var parkingToCreate = newParkingRequestMapper.toDomain(newParkingRequest);

        String name = parkingToCreate.getName();

        var parking = parkingPort.findParkingByNameOptional(name);
        if (parking.isPresent()) {
            throw new InternalServerErrorException("Parqueadero con ese mismo nombre ya registrado");
        }
        var parkingTypeToSave = parkingTypePort.findParkingTypeByType(newParkingRequest.getParkingType().getType());
        var openingHoursToSave = parkingToCreate.getOpeningHours();
        var locationToSave = parkingToCreate.getLocation();
        
        var locationCreated = locationPort.save(locationToSave, cityPort.findCityByCity(newParkingRequest.getLocation().getCity().getCity()));
        var parkingCreated = parkingPort.save(parkingToCreate, locationCreated, parkingTypeToSave,openingHoursToSave);

        
        ParkingSlotRequest newParkingSlotRequest = new ParkingSlotRequest();
        for(int i=0;i<parkingCreated.getTotal_slots();i++){
            newParkingSlotRequest.setParkingId(newParkingRequest);
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("1"),"CARRO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }

        return parkingDtoMapper.toDto(parkingCreated);
    }

    public ParkingDto getParking(String name){
        Parking parking = parkingPort.findParkingByName(name);
        return parkingDtoMapper.toDto(parking);
    }

    public List<ParkingDto> getParkings(){
        List<Parking> list = parkingPort.findParkings();
        List<ParkingDto> finalList = new ArrayList<ParkingDto>();
        for (Parking parking : list) {
            finalList.add(parkingDtoMapper.toDto(parking));
        }
        return finalList;
    }

    public ParkingDto modifyParking(NewParkingRequest newParkingRequest){
        Parking parkingToModify = newParkingRequestMapper.toDomain(newParkingRequest);
        Parking parking = parkingPort.findParkingByName(newParkingRequest.getName());
        //Modify parking values
        parking.setName(newParkingRequest.getName());
        parking.setAvailable_slots(Integer.valueOf(newParkingRequest.getAvailable_slots()));
        parking.setTotal_slots(Integer.valueOf(newParkingRequest.getTotal_slots()));
        parking.setLoyalty(Boolean.parseBoolean(newParkingRequest.getLoyalty()));
        var parkingTypeToSave = parkingTypePort.findParkingTypeByType(newParkingRequest.getParkingType().getType());
        var openingHoursToSave = parkingToModify.getOpeningHours();

        //Modify location values
        Location locationFound = locationPort.findLocationByIDLocation(parking.getLocation().getId());
        locationFound.setAddress(parkingToModify.getLocation().getAddress());
        locationFound.setLatitude(parkingToModify.getLocation().getLatitude());
        locationFound.setLongitude(parkingToModify.getLocation().getLongitude());
        Location locationCreated = locationPort.save(locationFound, cityPort.findCityByCity(newParkingRequest.getLocation().getCity().getCity()));
        
        var parkingModified = parkingPort.save(parking,locationCreated,parkingTypeToSave,openingHoursToSave);

        return parkingDtoMapper.toDto(parkingModified);
    }
    
    public String deleteParking(String name){
                Parking parkingToDelete = parkingPort.findParkingByName(name);
                Location locationToDelete = parkingToDelete.getLocation();
                OpeningHours openingHours = parkingToDelete.getOpeningHours();
                parkingPort.deleteParking(parkingToDelete);
                locationPort.deleteLocation(locationToDelete);
                openingHoursPort.deleteOpeningHours(openingHours);
                return "Eliminacion correcta";   
    }

    @Override
    public ParkingDto setAdmin(SetAdminToParkingRequest setAdminToParkingRequest) {
        Parking parking = parkingPort.findParkingByName(setAdminToParkingRequest.getParkingId().getName());
        var adminToSave = userPort.findUserById(Long.parseLong(setAdminToParkingRequest.getAdminId()));

        var parkingModified = parkingPort.save(parking,adminToSave);

        return parkingDtoMapper.toDto(parkingModified);

    }
}