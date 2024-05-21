package com.groupc.fourparks.infraestructure.adapter.repository;

import com.groupc.fourparks.infraestructure.adapter.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List <ReservationEntity> findByUserId(Long userId);
    @Query(value = "SELECT new ReservationEntity(r.id, r.reservationTime, r.reservationStartTime, r.reservationEndTime, r.totalPrice, r.user, r.parkingSlot) FROM ReservationEntity r JOIN ParkingSlotEntity ps ON r.parkingSlot.id = ps.id JOIN SlotStatusEntity ss ON ps.slotStatusId.id = ss.id JOIN ParkingEntity p ON ps.parkingId.id = p.id WHERE r.reservationEndTime IS NULL AND p.id = ?1")
    List <ReservationEntity> findActiveReservationsByParkingId(Long parkingId);
    @Query(value = "SELECT new ReservationEntity(r.id, r.reservationTime, r.reservationStartTime, r.reservationEndTime, r.totalPrice, r.user, r.parkingSlot) FROM ReservationEntity r WHERE r.reservationEndTime IS NULL AND r.user.id = ?1")
    List <ReservationEntity> findActiveReservationsByUserId(Long id);
    @Query(value = "SELECT new ReservationEntity(r.id, r.reservationTime, r.reservationStartTime, r.reservationEndTime, r.totalPrice, r.user, r.parkingSlot) FROM ReservationEntity r WHERE r.reservationEndTime IS NOT NULL AND r.user.id = ?1")
    List <ReservationEntity> findFinishReservationsByUserId(Long id);
}
