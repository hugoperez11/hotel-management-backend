package dev.hugo.hotel_management_backend.controller;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.service.ReservationService;
import dev.hugo.hotel_management_backend.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import dev.hugo.hotel_management_backend.dto.ReservationDto; 
import dev.hugo.hotel_management_backend.dto.CustomerDto;
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
            // Asegúrate de que las fechas sean convertidas a LocalDate
            checkInDate = LocalDate.parse(reservationDto.getCheckInDate());
            checkOutDate = LocalDate.parse(reservationDto.getCheckOutDate());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Usa el formato YYYY-MM-DD.", e);
        }
    
        // Verificar si la habitación está disponible usando las fechas convertidas
        boolean canReserve = reservationService.canReserveRoom(
            reservationDto.getRoomId(),  // Tipo Long
            checkInDate,  // Asegurarse de que se usa el LocalDate
            checkOutDate  // Asegurarse de que se usa el LocalDate
        );
        
        if (!canReserve) {
            throw new IllegalArgumentException("La habitación no está disponible para las fechas seleccionadas.");
        }
    
        // Crear o buscar el cliente basado en el nombre y el correo electrónico
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(reservationDto.getCustomerName());
        customerDto.setEmail(reservationDto.getCustomerEmail());
        
        Customer customer = customerService.createOrFindCustomer(customerDto);
        
        // Asignar el ID del cliente a la reserva
        reservationDto.setCustomerId(customer.getId());
        
        // Crear la reserva
        return reservationService.createReservation(reservationDto);
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
