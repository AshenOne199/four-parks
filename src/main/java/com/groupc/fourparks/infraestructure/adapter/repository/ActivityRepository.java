package com.groupc.fourparks.infraestructure.adapter.repository;


import com.groupc.fourparks.infraestructure.adapter.entity.ActivityEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long>{

}
