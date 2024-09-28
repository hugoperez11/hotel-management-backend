package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Room;
import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.repository.ReservationRepository;
import dev.hugo.hotel_management_backend.repository.RoomRepository;
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

    // Constructor
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    // Método para crear una reserva
    public Reservation createReservation(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Customer customer) {
        // Obtener la habitación
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La habitación no existe."));

        // Verificar si la habitación puede ser reservada
        if (!canReserveRoom(room.getId(), checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("La habitación no está disponible en esas fechas.");
        }

        // Crear una nueva reserva y asignar la habitación y el cliente
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCustomer(customer); // Usamos el objeto Customer directamente
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);

        // Guardar y retornar la reserva
        return reservationRepository.save(reservation);
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

        // Verificar disponibilidad de cada habitación
        for (Room room : allRooms) {
            boolean isAvailable = canReserveRoom(room.getId(), checkInDate, checkOutDate);
            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }
}
