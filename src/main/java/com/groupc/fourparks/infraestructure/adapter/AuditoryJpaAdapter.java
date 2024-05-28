package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.model.Auditory;
import com.groupc.fourparks.domain.port.AuditoryPort;
import com.groupc.fourparks.infraestructure.adapter.entity.AuditoryEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import com.groupc.fourparks.infraestructure.adapter.mapper.AuditoryDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.AuditoryRepository;


@Service
@AllArgsConstructor
public class AuditoryJpaAdapter  implements AuditoryPort {

    private final AuditoryDboMapper auditoryDboMapper;
    private final AuditoryRepository auditoryRepository;

    @Override
    public Auditory registerAuditory(Auditory auditory) {
        

        AuditoryEntity auditoryEntity = new AuditoryEntity();
        var saveable = auditoryDboMapper.toDbo(auditory);
        saveable.setHappeningDate(Date.from(Instant.now()));
        auditoryEntity = auditoryRepository.save(saveable);
        
        return auditoryDboMapper.toDomain(auditoryEntity);
    }

    @Override
    public List<Auditory> read() {
        List<Auditory> returnable = new ArrayList<>(); 
        List<AuditoryEntity> receiver = new ArrayList<>();
        receiver = auditoryRepository.findAll();
        for(AuditoryEntity auditoryEntity: receiver)
        {
            Auditory auditory = auditoryDboMapper.toDomain(auditoryEntity);
            auditory.setHappening_date(auditoryEntity.getHappeningDate());
            auditory.setUser(auditoryEntity.getUser());
            auditory.setActivity(auditoryEntity.getActivity());
            returnable.add(auditory);
        }
        

        return returnable;
    }
   

}
