package  com.groupc.fourparks.infraestructure.model.request;

import com.groupc.fourparks.infraestructure.adapter.entity.ActivityEntity;
import com.groupc.fourparks.infraestructure.adapter.entity.UserEntity;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditoryRequest {

    private String ip;
    private Date happening_date;
    private Long activity;
    private Long user;
}


