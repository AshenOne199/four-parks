package com.groupc.fourparks.infraestructure.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.groupc.fourparks.domain.model.City;
import com.groupc.fourparks.domain.model.Location;
import com.groupc.fourparks.domain.port.LocationPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.CityDboMapper;
import com.groupc.fourparks.infraestructure.adapter.mapper.LocationDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.LocationRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;

@Service
@AllArgsConstructor
public class LocationJpaAdapter implements LocationPort {
    final private LocationRepository locationRepository;

    final private LocationDboMapper locationDboMapper;

    final private CityDboMapper cityDboMapper;

    @Override
    public Location save(Location location, City City) {
        var locationToSave = locationDboMapper.toDbo(location);
        var cityToSave = cityDboMapper.toDbo(City);
        locationToSave.setCity(cityToSave);
        return locationDboMapper.toDomain(locationRepository.save(locationToSave));
    }

    @Override
    public Location save(Location location) {
        var locationToSave = locationDboMapper.toDbo(location);
        var locationSaved = locationRepository.save(locationToSave);
        return locationDboMapper.toDomain(locationSaved);
    }

    @Override
    public Location findLocationByIDLocation(Long id) {
        var optionalLocation = locationRepository.findLocationById(id);

        if (optionalLocation.isEmpty()){
            throw new NotFoundException("Id: " + id +" no registrado");
        }

        return locationDboMapper.toDomain(optionalLocation.get());
    }

    @Override
    public void deleteLocation(Location location) {
        var locationToDelete = locationDboMapper.toDbo(location);
        locationRepository.delete(locationToDelete);
    }

    @Override
    public Location findLocationByAddress(String address) {
        var optionalLocation = locationRepository.findLocationByAddress(address);

        if (optionalLocation.isEmpty()){
            throw new NotFoundException("Address: " + address +" no registrado");
        }

        return locationDboMapper.toDomain(optionalLocation.get());
    }

    @Override
    public Long findIdLocationByAddress(String address) {
        var optionalLocation = locationRepository.findLocationByAddress(address); 
        if (optionalLocation.isEmpty()){
            throw new NotFoundException("Address: " + address +" no registrado");
        }

        return optionalLocation.get().getId();
    }
}
