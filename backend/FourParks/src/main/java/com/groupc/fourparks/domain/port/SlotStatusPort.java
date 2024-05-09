package com.groupc.fourparks.domain.port;

import java.util.Optional;

import com.groupc.fourparks.domain.model.SlotStatus;

public interface SlotStatusPort {
    SlotStatus findSlotStatusByStatus(String status);
}
