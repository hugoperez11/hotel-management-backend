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
import java.util.Random;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final Random random = new Random();

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
        reservation.setCustomer(customer);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);

        // Generar y asignar el número de confirmación
        String confirmationNumber = generateUniqueConfirmationNumber();
        reservation.setConfirmationNumber(confirmationNumber);

        // Guardar y retornar la reserva
        return reservationRepository.save(reservation);
    }

    // Método para generar un número de confirmación único de 6 dígitos
    private String generateUniqueConfirmationNumber() {
        String confirmationNumber;
        int attempts = 0;
        do {
            confirmationNumber = String.format("C%06d", random.nextInt(1_000_000));
            attempts++;
            if (attempts > 10) { // Limitar los intentos para evitar un ciclo infinito
                throw new IllegalStateException("No se pudo generar un número de confirmación único después de 10 intentos.");
            }
        } while (reservationRepository.existsByConfirmationNumber(confirmationNumber));
        return confirmationNumber;
    }

    // Métodos existentes
    public List<Reservation> getReservationsByRoom(Long roomId) {
        return reservationRepository.findByRoom_Id(roomId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public boolean canReserveRoom(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        // Verificar si hay reservas que se solapen con las fechas solicitadas
        return reservationRepository.countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
            roomId, checkOutDate, checkInDate) == 0;
    }

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
