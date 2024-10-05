package dev.hugo.hotel_management_backend.controller;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.service.ReservationService;
import dev.hugo.hotel_management_backend.service.CustomerService;
import dev.hugo.hotel_management_backend.dto.CustomerDto;
import dev.hugo.hotel_management_backend.dto.ReservationDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Método POST existente para crear reservas
    @PostMapping
    public ReservationDto createReservation(@RequestBody ReservationDto reservationDto) {
        // [Código existente para crear una reserva]
        // ...

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
        responseDto.setCustomerName(reservation.getCustomer().getName());
        responseDto.setCustomerEmail(reservation.getCustomer().getEmail());

        return responseDto;
    }

    // Nuevo método GET para buscar una reserva por número de confirmación
    @GetMapping("/confirmation/{confirmationNumber}")
    public ReservationDto getReservationByConfirmationNumber(@PathVariable String confirmationNumber) {
        Reservation reservation = reservationService.findByConfirmationNumber(confirmationNumber)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada con el número de confirmación proporcionado."));

        // Convertir la entidad Reservation a ReservationDto para la respuesta
        ReservationDto responseDto = new ReservationDto();
        responseDto.setRoomId(reservation.getRoom().getId());
        responseDto.setCheckInDate(reservation.getCheckInDate().toString());
        responseDto.setCheckOutDate(reservation.getCheckOutDate().toString());
        responseDto.setCustomerId(reservation.getCustomer().getId());
        responseDto.setConfirmationNumber(reservation.getConfirmationNumber());
        responseDto.setCustomerName(reservation.getCustomer().getName());
        responseDto.setCustomerEmail(reservation.getCustomer().getEmail());

        return responseDto;
    }

    // **Nuevo método PUT para actualizar reservas usando el número de confirmación**
    @PutMapping("/confirmation/{confirmationNumber}")
    public ReservationDto updateReservationByConfirmationNumber(
            @PathVariable String confirmationNumber,
            @RequestBody ReservationDto reservationDto) {
        // Convertir las fechas de String a LocalDate
        LocalDate checkInDate;
        LocalDate checkOutDate;

        try {
            checkInDate = LocalDate.parse(reservationDto.getCheckInDate());
            checkOutDate = LocalDate.parse(reservationDto.getCheckOutDate());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Usa el formato YYYY-MM-DD.", e);
        }

        // Obtener el cliente existente o crear uno nuevo
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

        // Llamar al servicio para actualizar la reserva por número de confirmación
        Reservation updatedReservation = reservationService.updateReservationByConfirmationNumber(
            confirmationNumber,
            reservationDto.getRoomId(),
            checkInDate,
            checkOutDate,
            customer
        );

        // Convertir la entidad Reservation a ReservationDto para la respuesta
        ReservationDto responseDto = new ReservationDto();
        responseDto.setRoomId(updatedReservation.getRoom().getId());
        responseDto.setCheckInDate(updatedReservation.getCheckInDate().toString());
        responseDto.setCheckOutDate(updatedReservation.getCheckOutDate().toString());
        responseDto.setCustomerId(updatedReservation.getCustomer().getId());
        responseDto.setConfirmationNumber(updatedReservation.getConfirmationNumber());
        responseDto.setCustomerName(updatedReservation.getCustomer().getName());
        responseDto.setCustomerEmail(updatedReservation.getCustomer().getEmail());

        return responseDto;
    }

       // Endpoint para eliminar una reserva por número de confirmación
    @DeleteMapping("/confirmation/{confirmationNumber}")
    public ResponseEntity<String> deleteReservation(@PathVariable String confirmationNumber) {
        boolean isDeleted = reservationService.deleteReservationByConfirmationNumber(confirmationNumber);

        if (isDeleted) {
            return ResponseEntity.ok("Reserva eliminada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la reserva con ese número de confirmación");
        }
    }

    // Métodos GET existentes
    @GetMapping("/{roomId}")
    public List<Reservation> getReservationsByRoom(@PathVariable Long roomId) {
        return reservationService.getReservationsByRoom(roomId);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
