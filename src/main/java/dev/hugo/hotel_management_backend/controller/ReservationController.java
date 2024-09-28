package dev.hugo.hotel_management_backend.controller;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.service.ReservationService;
import dev.hugo.hotel_management_backend.service.CustomerService;
import dev.hugo.hotel_management_backend.dto.ReservationDto;
import dev.hugo.hotel_management_backend.dto.CustomerDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class ReservationController {

    private final ReservationService reservationService;
    private final CustomerService customerService;

    public ReservationController(ReservationService reservationService, CustomerService customerService) {
        this.reservationService = reservationService;
        this.customerService = customerService;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationDto reservationDto) {
        // Convertir las fechas de String a LocalDate
        LocalDate checkInDate;
        LocalDate checkOutDate;

        try {
            checkInDate = LocalDate.parse(reservationDto.getCheckInDate());
            checkOutDate = LocalDate.parse(reservationDto.getCheckOutDate());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Usa el formato YYYY-MM-DD.", e);
        }

        // Crear o encontrar el cliente
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(reservationDto.getCustomerName());
        customerDto.setEmail(reservationDto.getCustomerEmail());
        Customer customer = customerService.createOrFindCustomer(customerDto);

        // Crear la reserva usando el servicio
        return reservationService.createReservation(
            reservationDto.getRoomId(), 
            checkInDate, 
            checkOutDate, 
            customer
        );
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
