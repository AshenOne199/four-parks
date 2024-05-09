package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.SlotStatusEntity;



@Repository
public interface SlotStatusRepository extends JpaRepository<SlotStatusEntity, Long>{
    Optional<SlotStatusEntity> findByStatus(String status);
}
