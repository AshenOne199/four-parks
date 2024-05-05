package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.domain.model.OpeningHours;

public interface OpeningHoursPort {
    OpeningHours save(OpeningHours openingHours);
    void deleteOpeningHours(OpeningHours openingHours);
    
}
