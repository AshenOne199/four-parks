package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Auditory;
import com.groupc.fourparks.domain.model.User;
import com.groupc.fourparks.domain.port.AuditoryPort;
import com.groupc.fourparks.infraestructure.adapter.entity.AuditoryEntity;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import com.groupc.fourparks.infraestructure.adapter.mapper.AuditoryDboMapper;


@Service
@AllArgsConstructor
public class AuditoryJpaAdapter  implements AuditoryPort {

    private final  AuditoryDboMapper auditoryDboMapper;
    @Override
    public Auditory save(Auditory Auditory) {


        AuditoryEntity auditToSave = auditoryDboMapper.toDbo(Auditory);


        /*var userSaved = userRepository.save(userToSave);
        return userDboMapper.toDomain(userSaved);*/
        return null;
    }

}
