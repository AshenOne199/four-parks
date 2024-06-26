package com.groupc.fourparks.infraestructure.adapter.repository;


import com.groupc.fourparks.infraestructure.adapter.entity.AuditoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AuditoryRepository extends JpaRepository<AuditoryEntity, Long>{

}
