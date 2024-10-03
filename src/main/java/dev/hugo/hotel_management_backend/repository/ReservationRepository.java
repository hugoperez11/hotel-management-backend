package dev.hugo.hotel_management_backend.repository;

import dev.hugo.hotel_management_backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    // Método para contar reservas que se superponen en las fechas
    long countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
        Long roomId, LocalDate checkOutDate, LocalDate checkInDate);    
    
    // Método para obtener todas las reservas de una habitación
    List<Reservation> findByRoom_Id(Long roomId);

     // Nuevo método para verificar la existencia de un número de confirmación
     boolean existsByConfirmationNumber(String confirmationNumber);
}
