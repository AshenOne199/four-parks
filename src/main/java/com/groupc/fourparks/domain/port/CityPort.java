package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.City;

public interface CityPort {
    City findCityByCity(String name);
}
