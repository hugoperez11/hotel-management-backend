package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Reservation;

import dev.hugo.hotel_management_backend.model.Room; // Asegúrate de importar la clase Room
import dev.hugo.hotel_management_backend.dto.ReservationDto;
import dev.hugo.hotel_management_backend.model.Customer; // Asegúrate de importar la clase Customer
import dev.hugo.hotel_management_backend.repository.ReservationRepository;
import dev.hugo.hotel_management_backend.repository.RoomRepository; // Importa RoomRepository
import dev.hugo.hotel_management_backend.repository.CustomerRepository; // Importa CustomerRepository
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository; // Declara RoomRepository
    private final CustomerRepository customerRepository; // Declara CustomerRepository

    // Constructor
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository; // Inicializa RoomRepository
        this.customerRepository = customerRepository; // Inicializa CustomerRepository
    }

    // Método para crear una reserva
    public Reservation createReservation(ReservationDto reservationDto) {
        // Obtener la habitación
        Room room = roomRepository.findById(reservationDto.getRoomId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La habitación no existe."));

        // Obtener el cliente
        Customer customer = customerRepository.findById(reservationDto.getCustomerId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no existe."));

        // Verificar si la habitación puede ser reservada
        if (!canReserveRoom(room.getId(), reservationDto.getCheckInDate(), reservationDto.getCheckOutDate())) {
            throw new IllegalArgumentException("La habitación no está disponible en esas fechas.");
        }

        // Crear una nueva reserva y asignar la habitación y el cliente
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCustomer(customer);
        reservation.setCheckInDate(reservationDto.getCheckInDate());
        reservation.setCheckOutDate(reservationDto.getCheckOutDate());

        return reservationRepository.save(reservation); // Guardar la reserva
    }

    // Método para obtener reservas por habitación
    public List<Reservation> getReservationsByRoom(Long roomId) {
        return reservationRepository.findByRoom_Id(roomId);
    }

    // Método para obtener todas las reservas
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Método para verificar si una habitación puede ser reservada
    public boolean canReserveRoom(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return reservationRepository.countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
            roomId, checkOutDate, checkInDate) == 0;
    }

    // Método para obtener habitaciones disponibles
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        List<Room> allRooms = roomRepository.findAll(); // Obtener todas las habitaciones
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : allRooms) {
            boolean isAvailable = canReserveRoom(room.getId(), checkInDate, checkOutDate);
            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }
}
