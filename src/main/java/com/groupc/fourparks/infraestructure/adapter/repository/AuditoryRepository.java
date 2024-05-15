package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.Optional;

import com.groupc.fourparks.domain.model.Auditory;
import com.groupc.fourparks.infraestructure.adapter.entity.AuditoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.CityEntity;


public interface AuditoryRepository extends JpaRepository<AuditoryEntity, Long>{

}
