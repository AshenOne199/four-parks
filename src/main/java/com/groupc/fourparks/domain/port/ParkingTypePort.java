package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.ParkingType;

public interface ParkingTypePort {
    ParkingType findParkingTypeByType(String type);
}
