package dev.hugo.hotel_management_backend.controller;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.service.ReservationService;
import dev.hugo.hotel_management_backend.service.CustomerService;
import dev.hugo.hotel_management_backend.dto.CustomerDto;
import dev.hugo.hotel_management_backend.dto.ReservationDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ReservationDto createReservation(@RequestBody ReservationDto reservationDto) {
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
        Customer customer;
        if (reservationDto.getCustomerId() != null) {
            customer = customerService.findCustomerById(reservationDto.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no existe."));
        } else {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setName(reservationDto.getCustomerName());
            customerDto.setEmail(reservationDto.getCustomerEmail());
            customer = customerService.createOrFindCustomer(customerDto);
        }
    
        // Aquí puedes manejar la lógica para guardar la tarjeta de crédito y DNI si es necesario.
        // Por ejemplo, podrías querer almacenar esto en una base de datos, 
        // o verificarlo según tu modelo de negocio.
    
        // Crear la reserva usando el servicio
        Reservation reservation = reservationService.createReservation(
            reservationDto.getRoomId(), 
            checkInDate, 
            checkOutDate, 
            customer
        );
    
        // Convertir la entidad Reservation a ReservationDto para la respuesta
        ReservationDto responseDto = new ReservationDto();
        responseDto.setRoomId(reservation.getRoom().getId());
        responseDto.setCheckInDate(reservation.getCheckInDate().toString());
        responseDto.setCheckOutDate(reservation.getCheckOutDate().toString());
        responseDto.setCustomerId(reservation.getCustomer().getId());
        responseDto.setConfirmationNumber(reservation.getConfirmationNumber());
        
        // Set new fields
        responseDto.setCreditCard(reservationDto.getCreditCard()); // Optionally, store or process this
        responseDto.setDni(reservationDto.getDni()); // Optionally, store or process this
    
        return responseDto;
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
