package com.groupc.fourparks.application.service;

import com.groupc.fourparks.application.mapper.*;
import com.groupc.fourparks.infraestructure.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.groupc.fourparks.application.usecase.ParkingService;
import com.groupc.fourparks.domain.model.City;
import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingRate;
import com.groupc.fourparks.domain.model.ParkingSlot;
import com.groupc.fourparks.domain.port.CityPort;
import com.groupc.fourparks.domain.port.LocationPort;
import com.groupc.fourparks.domain.port.OpeningHoursPort;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.domain.port.ParkingRatePort;
import com.groupc.fourparks.domain.port.ParkingSlotPort;
import com.groupc.fourparks.domain.port.ParkingTypePort;
import com.groupc.fourparks.domain.port.UserPort;
import com.groupc.fourparks.infraestructure.exception.BadRequestException;
import com.groupc.fourparks.infraestructure.exception.InternalServerErrorException;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;
import com.groupc.fourparks.infraestructure.model.request.SlotStatusRequest;
import com.groupc.fourparks.infraestructure.model.request.VehicleTypeRequest;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingServiceImpl implements ParkingService{

    private final NewParkingRequestMapper newParkingRequestMapper;
    private final CityDtoMapper cityDtoMapper;
    private final LocationDtoMapper locationDtoMapper;
    private final ParkingTypeDtoMapper parkingTypeDtoMapper;
    private final OpeningHoursDtoMapper openingHoursDtoMapper;
    private final ParkingRateDtoMapper parkingRateDtoMapper;

    private final ParkingPort parkingPort;
    private final LocationPort locationPort;
    private final OpeningHoursPort openingHoursPort;
    private final CityPort cityPort;
    private final ParkingTypePort parkingTypePort;
    private final UserPort userPort;
    private final ParkingSlotPort parkingSlotPort;
    private final ParkingRatePort parkingRatePort;

    private final ParkingSlotServiceImpl parkingSlotServiceImpl;

    @Transactional
    public ParkingDto newParking(NewParkingRequest newParkingRequest) {
        var parkingToCreate = newParkingRequestMapper.toDomain(newParkingRequest);

        var adminToSet = userPort.findUserById(Long.parseLong(String.valueOf(newParkingRequest.getAdminId())));
        if (adminToSet.getRoles().stream().noneMatch(rol -> rol.getRoleEnum().name().equals("ADMINISTRADOR"))) {
            throw new BadRequestException("El adminId no pertenece a un usuario con rol administrador");
        }
        parkingPort.findParkingByAdminId(adminToSet.getId());
        parkingToCreate.setAdmin(adminToSet);

        String name = parkingToCreate.getName();
        var parking = parkingPort.findParkingByNameOptional(name);
        if (parking.isPresent()) {
            throw new InternalServerErrorException("Parqueadero con ese mismo nombre ya registrado");
        }
        parkingToCreate.setTotalSlots(newParkingRequest.getCarSlots()+newParkingRequest.getMotorcycleSlots()+newParkingRequest.getBicycleSlots()+newParkingRequest.getHeavyVehicleSlots());
        parkingToCreate.setAvailableSlots(parkingToCreate.getTotalSlots());

        parkingToCreate.setCarSlots(newParkingRequest.getCarSlots());
        parkingToCreate.setMotorcycleSlots(newParkingRequest.getMotorcycleSlots());
        parkingToCreate.setBicycleSlots(newParkingRequest.getBicycleSlots());
        parkingToCreate.setHeavyVehicleSlots(newParkingRequest.getHeavyVehicleSlots());

        var parkingTypeToSave = parkingTypePort.findParkingTypeByType(newParkingRequest.getParkingType().getType());
        var openingHoursToSave = parkingToCreate.getOpeningHours();
        var locationToSave = parkingToCreate.getLocation();
        locationPort.findLocationByAddress(locationToSave.getAddress());
        var locationCreated = locationPort.save(locationToSave, cityPort.findCityByCity(newParkingRequest.getLocation().getCity().getCity()));
        var parkingCreated = parkingPort.save(parkingToCreate, locationCreated, parkingTypeToSave, openingHoursToSave);

        ParkingSlotRequest newParkingSlotRequest = new ParkingSlotRequest();
        for(int i = 0; i<parkingCreated.getCarSlots(); i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("1"),"CARRO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i = 0; i<parkingCreated.getMotorcycleSlots(); i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("2"),"MOTO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i = 0; i<parkingCreated.getBicycleSlots(); i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("3"),"BICICLETA"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i = 0; i<parkingCreated.getHeavyVehicleSlots(); i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("4"),"VEHICULO_PESADO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }

        return this.parkingToAddListConvert(parkingCreated);
    }

    public ParkingDto getParking(String name){
        Parking parking = parkingPort.findParkingByName(name);
        return this.parkingToAddListConvert(parking);
    }

    public List<ParkingDto> getParkings(){
        List<Parking> list = parkingPort.findParkings();
        List<ParkingDto> finalList = new ArrayList<>();
        for (Parking parking : list) {
            finalList.add(parkingToAddListConvert(parking));
        }
        return finalList;
    }

    public ParkingDto modifyParking(NewParkingRequest newParkingRequest){
        Parking parkingToModify = newParkingRequestMapper.toDomain(newParkingRequest);
        Parking parking = parkingPort.findParkingByName(newParkingRequest.getName());
        int lastCarSlots = parking.getCarSlots();
        int lastMotorcycleSlots = parking.getMotorcycleSlots();
        int lastBicycleSlots = parking.getBicycleSlots();
        int lastHeavyVehicleSlots = parking.getHeavyVehicleSlots();
        parking.setName(newParkingRequest.getName());
        Integer totalSlots = newParkingRequest.getCarSlots()+newParkingRequest.getMotorcycleSlots()+newParkingRequest.getBicycleSlots()+newParkingRequest.getHeavyVehicleSlots();
        parking.setTotalSlots(totalSlots);
        parking.setAvailableSlots(parking.getTotalSlots());
        parking.setCarSlots(newParkingRequest.getCarSlots());
        parking.setMotorcycleSlots(newParkingRequest.getMotorcycleSlots());
        parking.setBicycleSlots(newParkingRequest.getBicycleSlots());
        parking.setHeavyVehicleSlots(newParkingRequest.getHeavyVehicleSlots());
        parking.setLoyalty(newParkingRequest.getLoyalty());

        var parkingTypeToSave = parkingTypePort.findParkingTypeByType(newParkingRequest.getParkingType().getType());
        var openingHoursToSave = parkingToModify.getOpeningHours();
        var adminToSet = userPort.findUserById(Long.parseLong(String.valueOf(newParkingRequest.getAdminId())));
        parking.setAdmin(adminToSet);

        //Modify location values
        Location locationFound = locationPort.findLocationByIDLocation(parking.getLocation().getId());
        locationFound.setAddress(parkingToModify.getLocation().getAddress());
        locationFound.setLatitude(parkingToModify.getLocation().getLatitude());
        locationFound.setLongitude(parkingToModify.getLocation().getLongitude());
        Location locationCreated = locationPort.save(locationFound, cityPort.findCityByCity(newParkingRequest.getLocation().getCity().getCity()));
        
        var parkingModified = parkingPort.save(parking,locationCreated,parkingTypeToSave,openingHoursToSave);

        if(lastCarSlots<parkingModified.getCarSlots()){
            for(int i = parking.getCarSlots(); i<=parkingModified.getCarSlots(); i++){
                parkingSlotServiceImpl.newParkingSlot(createParkingSlot(parkingModified.getId(), 1L, "EMPTY", 1L, "CARRO"));
            }
        }
        if(lastMotorcycleSlots<parkingModified.getMotorcycleSlots()){
            for(int i = parking.getMotorcycleSlots(); i<=parkingModified.getMotorcycleSlots(); i++){
                parkingSlotServiceImpl.newParkingSlot(createParkingSlot(parkingModified.getId(), 1L, "EMPTY", 2L, "MOTO"));
            }
        }
        
        if(lastBicycleSlots<parkingModified.getBicycleSlots()){
            for(int i = parking.getBicycleSlots(); i<=parkingModified.getBicycleSlots(); i++){
                parkingSlotServiceImpl.newParkingSlot(createParkingSlot(parkingModified.getId(), 1L, "EMPTY", 3L, "BICICLETA"));
            }
        }
        
        if(lastHeavyVehicleSlots<parkingModified.getHeavyVehicleSlots()){
            for(int i = parking.getHeavyVehicleSlots(); i<=parkingModified.getHeavyVehicleSlots(); i++){
                parkingSlotServiceImpl.newParkingSlot(createParkingSlot(parkingModified.getId(), 1L, "EMPTY", 3L, "VEHICULO_PESADO"));
            }
        }

        return this.parkingToAddListConvert(parkingModified);
    }

    public ParkingSlotRequest createParkingSlot(Long parkingId, Long slotId, String slotStatus, Long vehicleTypeId, String vehicleType) {
        ParkingSlotRequest request = new ParkingSlotRequest();
        request.setParkingId(parkingId);
        request.setSlotStatusId(new SlotStatusRequest(slotId,slotStatus));
        request.setVehicleTypeId(new VehicleTypeRequest(vehicleTypeId,vehicleType));
        return request;
    }
    
    public String deleteParking(String name){
        //Delete all parking slots
        List<ParkingSlot> slotsToDelete = parkingSlotPort.getParkingSlotsByParking(parkingPort.findParkingByName(name));
        if(!slotsToDelete.isEmpty()){
            for (ParkingSlot parkingSlot : slotsToDelete) {
                parkingSlotPort.deleteParkingSlot(parkingSlot);
            }
        }

        //Delete all parking rates
        List<ParkingRate> ratesToDelete = parkingRatePort.getParkingRatesByParking(parkingPort.findParkingByName(name));
        if(!ratesToDelete.isEmpty()){
            for (ParkingRate parkingRate : ratesToDelete) {
                parkingRatePort.deleteParkingRate(parkingRate);
            }
        }
        Parking parkingToDelete = parkingPort.findParkingByName(name);
        Location locationToDelete = parkingToDelete.getLocation();
        OpeningHours openingHours = parkingToDelete.getOpeningHours();

        parkingPort.deleteParking(parkingToDelete);
        locationPort.deleteLocation(locationToDelete);
        openingHoursPort.deleteOpeningHours(openingHours);
        return "Eliminacion correcta";   
    }

    @Override
    public List<CityDto> getCities() {
        List<City> list = cityPort.findAll();
        List<CityDto> finalList = new ArrayList<>();
        for (City city : list) {
            finalList.add(cityDtoMapper.toDto(city));
        }
        return finalList;
    }

    @Override
    public ParkingDto getParkingById(Long id) {
        Parking parking = parkingPort.findById(id);
        return this.parkingToAddListConvert(parking);
    }


    private ParkingDto parkingToAddListConvert(Parking parking) {
        List<ParkingRate> rates = parkingRatePort.getParkingRatesByParking(parking);
        List<ParkingRateDto> ratesDto = rates.stream()
                .map(parkingRateDtoMapper::toDto)
                .toList();

        List<ParkingSlotDetailsDto> slotsEmpty = parkingSlotPort.findEmptySlotsByParkingId(parking.getId());

        return ParkingDto.builder()
                .id(parking.getId())
                .name(parking.getName())
                .availableSlots(parking.getAvailableSlots())
                .totalSlots(parking.getTotalSlots())
                .carSlots(parking.getCarSlots())
                .motorcycleSlots(parking.getMotorcycleSlots())
                .bicycleSlots(parking.getBicycleSlots())
                .heavyVehicleSlots(parking.getHeavyVehicleSlots())
                .loyalty(parking.getLoyalty())
                .admin(convertToDto(parking))
                .location(locationDtoMapper.toDto(parking.getLocation()))
                .parkingType(parkingTypeDtoMapper.toDto(parking.getParkingType()))
                .openingHours(openingHoursDtoMapper.toDto(parking.getOpeningHours()))
                .parkingRate(ratesDto)
                .parkingSlotDetails(slotsEmpty)
                .build();
    }

    public UserDto convertToDto(Parking parking) {
        return UserDto.builder()
                .id(parking.getAdmin().getId())
                .email(parking.getAdmin().getEmail())
                .firstName(parking.getAdmin().getFirstName())
                .secondName(parking.getAdmin().getSecondName())
                .firstLastname(parking.getAdmin().getFirstLastname())
                .secondLastname(parking.getAdmin().getSecondLastname())
                .build();
    }
}
