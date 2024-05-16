package com.groupc.fourparks.infraestructure.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.domain.model.City;
import com.groupc.fourparks.domain.port.CityPort;
import com.groupc.fourparks.infraestructure.adapter.entity.CityEntity;
import com.groupc.fourparks.infraestructure.adapter.mapper.CityDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.CityRepository;
import com.groupc.fourparks.infraestructure.exception.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CityJpaAdapter  implements CityPort{
    private final CityRepository cityRepository;

    private final CityDboMapper cityDboMapper;
    @Override
    public City findCityByCity(String City) {
        var optionalCity = cityRepository.findCityEntityByCity(City);

        if (optionalCity.isEmpty()){
            throw new NotFoundException("City: " + City +" no registrado");
        }

        return cityDboMapper.toDomain(optionalCity.get());
    }
    @Override
    public List<City> findAll() {
        List<City> allCities = new ArrayList<>(List.of());
        List<CityEntity> citiesReceiver = cityRepository.findAll();
        for (CityEntity cityEntity : citiesReceiver) {
            allCities.add(cityDboMapper.toDomain(cityEntity));
        }
        return  allCities;
    }
}
