package com.groupc.fourparks.domain.port;

import java.util.List;

import com.groupc.fourparks.domain.model.Auditory;

public interface AuditoryPort {
    Auditory registerAuditory(Auditory Auditory);
    List<Auditory> read();
}
