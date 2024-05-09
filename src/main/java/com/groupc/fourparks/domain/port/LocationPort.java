package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.City;
import com.groupc.fourparks.domain.model.Location;

public interface LocationPort {
    Location save(Location location, City city);
    Location save(Location location);
    Location findLocationByIDLocation(Long id);
    Location findLocationByAddress(String address);
    Long findIdLocationByAddress(String address);
    void deleteLocation(Location location);
}
