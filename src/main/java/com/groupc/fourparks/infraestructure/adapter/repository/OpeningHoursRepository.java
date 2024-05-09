package com.groupc.fourparks.infraestructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.OpeningHoursEntity;

@Repository
public interface OpeningHoursRepository extends JpaRepository<OpeningHoursEntity, Long>{
    
}
