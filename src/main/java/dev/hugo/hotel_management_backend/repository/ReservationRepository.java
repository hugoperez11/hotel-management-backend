package dev.hugo.hotel_management_backend.repository;

import dev.hugo.hotel_management_backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoom_Id(Long roomId);

    boolean existsByConfirmationNumber(String confirmationNumber);

    long countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThan(Long roomId, LocalDate checkOutDate, LocalDate checkInDate);

    // Nuevo método para contar excluyendo una reserva específica
    long countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThanAndIdNot(Long roomId, LocalDate checkOutDate, LocalDate checkInDate, Long id);

    // Nuevo método para buscar por número de confirmación
    Optional<Reservation> findByConfirmationNumber(String confirmationNumber);
}
