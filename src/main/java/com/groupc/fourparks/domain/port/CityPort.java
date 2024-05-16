package com.groupc.fourparks.domain.port;

import java.util.List;

import com.groupc.fourparks.domain.model.City;

public interface CityPort {
    City findCityByCity(String name);
    List<City> findAll();
}
