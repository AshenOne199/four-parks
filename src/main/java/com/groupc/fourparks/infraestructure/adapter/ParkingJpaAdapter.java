package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.domain.model.Parking;
import com.groupc.fourparks.domain.model.ParkingType;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.ParkingPort;
import com.groupc.fourparks.infraestructure.adapter.entity.ParkingEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.LocationDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.OpeningHoursDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.ParkingTypeDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.UserDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.ParkingRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParkingJpaAdapter implements ParkingPort{

    final private ParkingRepository parkingRepository;

    final private ParkingDboMapper parkingDboMapper;

    final private LocationDboMapper locationDboMapper;

    final private UserDboMapper userDboMapper;

    final private ParkingTypeDboMapper parkingTypeDboMapper;

    final private OpeningHoursDboMapper openingHoursDboMapper;

    @Override
    public Parking save(Parking parking) {
        var parkingToSave = parkingDboMapper.toDbo(parking);
        var parkingSaved = parkingRepository.save(parkingToSave);
        return parkingDboMapper.toDomain(parkingSaved);
    }

    @Override
    public Parking findParkingByName(String name) {
        var optionalParking = parkingRepository.findParkingEntityByName(name);

        if (optionalParking.isEmpty()){
            throw new NotFoundException("Name: " + name +" no registrado");
        }

        return parkingDboMapper.toDomain(optionalParking.get());
    }

    @Override
    public Optional<Parking> findParkingByNameOptional(String name) {
        var optionalParking = parkingRepository.findParkingEntityByName(name);
        return optionalParking.map(parkingDboMapper::toDomain);
    }

    @Override
    public Parking save(Parking parking, Location location, ParkingType parkingType, OpeningHours openingHours) {
        var parkingToSave = parkingDboMapper.toDbo(parking);
        var locationToSave = locationDboMapper.toDbo(location);
        var parkingTypeToSave = parkingTypeDboMapper.toDbo(parkingType);
        var openingHoursToSave = openingHoursDboMapper.toDbo(openingHours);
        parkingToSave.setLocation(locationToSave);
        parkingToSave.setParkingType(parkingTypeToSave);
        parkingToSave.setOpeningHours(openingHoursToSave);
        
        return parkingDboMapper.toDomain(parkingRepository.save(parkingToSave));
    }

    @Override
    public List<Parking> findParkings() {
        List<Parking> allParkings = new ArrayList<Parking>();
        List<ParkingEntity> parkingsReceiver = parkingRepository.findAll();
        for (ParkingEntity parkingEntity : parkingsReceiver) {
            allParkings.add(parkingDboMapper.toDomain(parkingEntity));
        }
        return allParkings;
    }

    @Override
    public void deleteParking(Parking parking) {
        var parkingToDelete = parkingDboMapper.toDbo(parking);
        parkingRepository.delete(parkingToDelete);
    }

    @Override
    public Parking findById(Long id) {
        var optionalParking = parkingRepository.findById(id);
        return optionalParking.map(parkingDboMapper::toDomain).get();
    }

    @Override
    public Parking save(Parking parking, User user) {
        var parkingToSave = parkingDboMapper.toDbo(parking);
        var userToSave = userDboMapper.toDbo(user);
        /*parkingToSave.setAdminId(userToSave);*/
        return parkingDboMapper.toDomain(parkingRepository.save(parkingToSave));
    }
}
