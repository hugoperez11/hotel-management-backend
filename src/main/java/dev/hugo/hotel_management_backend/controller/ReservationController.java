package dev.hugo.hotel_management_backend.controller;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import dev.hugo.hotel_management_backend.dto.ReservationDto; // Asegúrate de importar ReservationDto
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationDto reservationDto) { // Cambiado a ReservationDto
        return reservationService.createReservation(reservationDto); // Usa reservationDto
    }

    @GetMapping("/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable Long roomId) {
        return reservationService.getReservationsByRoom(roomId);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}

