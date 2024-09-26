package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Room;
import dev.hugo.hotel_management_backend.dto.ReservationDto;
import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.repository.ReservationRepository;
import dev.hugo.hotel_management_backend.repository.RoomRepository;
import dev.hugo.hotel_management_backend.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;

    // Constructor
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
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
        LocalDate checkInDate = LocalDate.parse(reservationDto.getCheckInDate());
        LocalDate checkOutDate = LocalDate.parse(reservationDto.getCheckOutDate());

        if (!canReserveRoom(room.getId(), checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("La habitación no está disponible en esas fechas.");
        }

        // Crear una nueva reserva y asignar la habitación y el cliente
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCustomer(customer);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);

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
        // Verificar si hay reservas que se solapen con las fechas solicitadas
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
