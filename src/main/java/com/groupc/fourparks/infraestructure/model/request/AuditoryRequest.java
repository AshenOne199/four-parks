package  com.groupc.fourparks.infraestructure.model.request;

import com.groupc.fourparks.infraestructure.adapter.entity.ActivityEntity;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditoryRequest {

    private String ip;
    private Date happening_date;
    private ActivityEntity activity;
}


