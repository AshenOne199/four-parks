package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.VehicleType;

public interface VehicleTypePort {
    VehicleType findVehicleTypeByType(String type);
}
