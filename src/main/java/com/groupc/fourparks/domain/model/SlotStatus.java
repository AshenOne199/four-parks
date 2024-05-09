package com.groupc.fourparks.domain.model;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotStatus {
    private long id;
    private SlotStatusEnum status;

}
