package com.groupc.fourparks.infraestructure.adapter.repository;

import com.groupc.fourparks.infraestructure.adapter.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Long> {
}
