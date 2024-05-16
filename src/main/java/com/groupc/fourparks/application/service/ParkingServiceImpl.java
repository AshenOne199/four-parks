package com.groupc.fourparks.application.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.groupc.fourparks.application.mapper.CityDtoMapper;
import com.groupc.fourparks.application.mapper.NewParkingRequestMapper;
import com.groupc.fourparks.application.mapper.ParkingToShowMapper;
import com.groupc.fourparks.application.mapper.UserToShowMapper;
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
import com.groupc.fourparks.infraestructure.model.dto.CityDto;
import com.groupc.fourparks.infraestructure.model.dto.UserDto;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;
import com.groupc.fourparks.infraestructure.model.request.ParkingToShow;
import com.groupc.fourparks.infraestructure.model.request.SlotStatusRequest;
import com.groupc.fourparks.infraestructure.model.request.VehicleTypeRequest;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParkingServiceImpl implements ParkingService{

    private final NewParkingRequestMapper newParkingRequestMapper;
    private final CityDtoMapper cityDtoMapper;
    private final ParkingToShowMapper parkingToShowMapper;

    private final ParkingPort parkingPort;
    private final LocationPort locationPort;
    private final OpeningHoursPort openingHoursPort;
    private final CityPort cityPort;
    private final ParkingTypePort parkingTypePort;
    private final UserPort userPort;
    private final UsersManageServiceImpl usersManageServiceImpl;
    private final ParkingSlotPort parkingSlotPort;
    private final ParkingRatePort parkingRatePort;

    private final ParkingSlotServiceImpl parkingSlotServiceImpl;
    
    

    @Transactional
    public ParkingToShow newParking(NewParkingRequest newParkingRequest) {
        boolean rolCheck=false;
        var parkingToCreate = newParkingRequestMapper.toDomain(newParkingRequest);

        var adminToSet = userPort.findUserById(Long.parseLong(String.valueOf(newParkingRequest.getAdminId())));
        parkingToCreate.setAdmin(adminToSet);
        for(UserDto userToShow: usersManageServiceImpl.userByRole(Long.parseLong("2"))){
            if(adminToSet.getEmail().equals(userToShow.getEmail()))
                rolCheck = true;
        }

        if (!rolCheck) {
            throw new BadRequestException("El usuario debe tener como rol Administrador");
        }

        String name = parkingToCreate.getName();
        var parking = parkingPort.findParkingByNameOptional(name);
        if (parking.isPresent()) {
            throw new InternalServerErrorException("Parqueadero con ese mismo nombre ya registrado");
        }
        parkingToCreate.setTotal_slots(newParkingRequest.getCar_slots()+newParkingRequest.getMotorcycle_slots()+newParkingRequest.getBicycle_slots()+newParkingRequest.getHeavy_vehicle_slots());
        parkingToCreate.setAvailable_slots(parkingToCreate.getTotal_slots());

        parkingToCreate.setCar_slots(newParkingRequest.getCar_slots());
        parkingToCreate.setMotorcycle_slots(newParkingRequest.getMotorcycle_slots());
        parkingToCreate.setBicycle_slots(newParkingRequest.getBicycle_slots());
        parkingToCreate.setHeavy_vehicle_slots(newParkingRequest.getHeavy_vehicle_slots());

        var parkingTypeToSave = parkingTypePort.findParkingTypeByType(newParkingRequest.getParkingType().getType());
        var openingHoursToSave = parkingToCreate.getOpeningHours();
        var locationToSave = parkingToCreate.getLocation();
        var locationCreated = locationPort.save(locationToSave, cityPort.findCityByCity(newParkingRequest.getLocation().getCity().getCity()));
        var parkingCreated = parkingPort.save(parkingToCreate, locationCreated, parkingTypeToSave, openingHoursToSave);

        ParkingSlotRequest newParkingSlotRequest = new ParkingSlotRequest();
        for(int i=0;i<parkingCreated.getCar_slots();i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("1"),"CARRO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i=0;i<parkingCreated.getMotorcycle_slots();i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("2"),"MOTO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i=0;i<parkingCreated.getBicycle_slots();i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("3"),"BICICLETA"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i=0;i<parkingCreated.getHeavy_vehicle_slots();i++){
            newParkingSlotRequest.setParkingId(parkingCreated.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("4"),"VEHICULO_PESADO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }

        return parkingToShowMapper.toShow(parkingCreated);
    }

    public ParkingToShow getParking(String name){
        Parking parking = parkingPort.findParkingByName(name);
        return parkingToShowMapper.toShow(parking);
    }

    public List<ParkingToShow> getParkings(){
        List<Parking> list = parkingPort.findParkings();
        List<ParkingToShow> finalList = new ArrayList<ParkingToShow>();
        for (Parking parking : list) {
            finalList.add(parkingToShowMapper.toShow(parking));
        }
        return finalList;
    }

    public ParkingToShow modifyParking(NewParkingRequest newParkingRequest){
        Parking parkingToModify = newParkingRequestMapper.toDomain(newParkingRequest);
        Parking parking = parkingPort.findParkingByName(newParkingRequest.getName());
        //Modify parking values
        parking.setName(newParkingRequest.getName());
        parking.setTotal_slots(newParkingRequest.getCar_slots()+newParkingRequest.getMotorcycle_slots()+newParkingRequest.getBicycle_slots()+newParkingRequest.getHeavy_vehicle_slots());
        parking.setAvailable_slots(parking.getTotal_slots());

        parking.setCar_slots(newParkingRequest.getCar_slots());
        parking.setMotorcycle_slots(newParkingRequest.getMotorcycle_slots());
        parking.setBicycle_slots(newParkingRequest.getBicycle_slots());
        parking.setHeavy_vehicle_slots(newParkingRequest.getHeavy_vehicle_slots());

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
        
        List<ParkingSlot> slotsToDelete = parkingSlotPort.getParkingSlotsByParking(parkingPort.findParkingByName(parkingModified.getName()));
        if(slotsToDelete.size()>0){
            for (ParkingSlot parkingSlot : slotsToDelete) {
                parkingSlotPort.deleteParkingSlot(parkingSlot);
            }
        }
        ParkingSlotRequest newParkingSlotRequest = new ParkingSlotRequest();
        for(int i=0;i<parkingModified.getCar_slots();i++){
            newParkingSlotRequest.setParkingId(parkingModified.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("1"),"CARRO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i=0;i<parkingModified.getMotorcycle_slots();i++){
            newParkingSlotRequest.setParkingId(parkingModified.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("2"),"MOTO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i=0;i<parkingModified.getBicycle_slots();i++){
            newParkingSlotRequest.setParkingId(parkingModified.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("3"),"BICICLETA"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }
        for(int i=0;i<parkingModified.getHeavy_vehicle_slots();i++){
            newParkingSlotRequest.setParkingId(parkingModified.getId());
            newParkingSlotRequest.setSlotStatusId(new SlotStatusRequest(Long.parseLong("1"),"EMPTY"));
            newParkingSlotRequest.setVehicleTypeId(new VehicleTypeRequest(Long.parseLong("4"),"VEHICULO_PESADO"));
            parkingSlotServiceImpl.newParkingSlot(newParkingSlotRequest);
        }

        return parkingToShowMapper.toShow(parkingModified);
    }
    
    public String deleteParking(String name){
        //Delete all parking slots
        List<ParkingSlot> slotsToDelete = parkingSlotPort.getParkingSlotsByParking(parkingPort.findParkingByName(name));
        if(slotsToDelete.size()>0){
            for (ParkingSlot parkingSlot : slotsToDelete) {
                parkingSlotPort.deleteParkingSlot(parkingSlot);
            }
        }

        //Delete all parking rates
        List<ParkingRate> ratesToDelete = parkingRatePort.getParkingRatesByParking(parkingPort.findParkingByName(name));
        if(ratesToDelete.size()>0){
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
        List<CityDto> finalList = new ArrayList<CityDto>();
        for (City city : list) {
            finalList.add(cityDtoMapper.toDto(city));
        }
        return finalList;
    }
}
