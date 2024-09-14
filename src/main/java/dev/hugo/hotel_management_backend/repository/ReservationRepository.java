package dev.hugo.hotel_management_backend.repository;

import dev.hugo.hotel_management_backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

