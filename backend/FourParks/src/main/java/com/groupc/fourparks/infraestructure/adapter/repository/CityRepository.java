package com.groupc.fourparks.infraestructure.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupc.fourparks.infraestructure.adapter.entity.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long>{
    Optional<CityEntity> findCityEntityByCity(String city);
}
