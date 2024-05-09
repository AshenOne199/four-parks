package com.groupc.fourparks.infraestructure.adapter;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import com.groupc.fourparks.domain.model.OpeningHours;
import com.groupc.fourparks.domain.port.OpeningHoursPort;
import com.groupc.fourparks.infraestructure.adapter.mapper.OpeningHoursDboMapper;
import com.groupc.fourparks.infraestructure.adapter.repository.OpeningHoursRepository;

@Service
@AllArgsConstructor
public class OpeningHoursJpaAdapter implements OpeningHoursPort{

    final private OpeningHoursRepository openingHoursRepository;

    final private OpeningHoursDboMapper openingHoursDboMapper;

    @Override
    public OpeningHours save(OpeningHours openingHours) {
        var openingHoursToSave = openingHoursDboMapper.toDbo(openingHours);
        var openingHoursSaved = openingHoursRepository.save(openingHoursToSave);
        return openingHoursDboMapper.toDomain(openingHoursSaved);
    }

    @Override
    public void deleteOpeningHours(OpeningHours openingHours) {
        var openingHoursToDelete = openingHoursDboMapper.toDbo(openingHours);
        openingHoursRepository.delete(openingHoursToDelete);
    }
    
}
