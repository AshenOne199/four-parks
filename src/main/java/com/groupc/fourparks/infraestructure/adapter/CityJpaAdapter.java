package com.groupc.fourparks.infraestructure.adapter;

import org.springframework.stereotype.Service;

import com.groupc.fourparks.domain.model.City;
import com.groupc.fourparks.domain.port.CityPort;
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
    
}
