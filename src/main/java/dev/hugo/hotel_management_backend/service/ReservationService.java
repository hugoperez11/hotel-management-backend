package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Room;
import dev.hugo.hotel_management_backend.model.Customer;
import dev.hugo.hotel_management_backend.repository.ReservationRepository;
import dev.hugo.hotel_management_backend.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final Random random = new Random();

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation createReservation(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Customer customer) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La habitación no existe."));
        if (!canReserveRoom(room.getId(), checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("La habitación no está disponible en esas fechas.");
        }
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCustomer(customer);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        String confirmationNumber = generateUniqueConfirmationNumber();
        reservation.setConfirmationNumber(confirmationNumber);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateReservationByConfirmationNumber(String confirmationNumber, Long newRoomId, LocalDate newCheckInDate, LocalDate newCheckOutDate, Customer newCustomer) {
        Reservation existingReservation = reservationRepository.findByConfirmationNumber(confirmationNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "La reserva con el número de confirmación proporcionado no existe."));
        Room newRoom = roomRepository.findById(newRoomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La habitación no existe."));
        if (!canReserveRoom(newRoom.getId(), newCheckInDate, newCheckOutDate, existingReservation.getId())) {
            throw new IllegalArgumentException("La habitación no está disponible en esas fechas.");
        }
        existingReservation.setRoom(newRoom);
        existingReservation.setCheckInDate(newCheckInDate);
        existingReservation.setCheckOutDate(newCheckOutDate);
        existingReservation.setCustomer(newCustomer);
        return reservationRepository.save(existingReservation);
    }

        // Método para eliminar una reserva por número de confirmación
        public boolean deleteReservationByConfirmationNumber(String confirmationNumber) {
            Optional<Reservation> reservation = reservationRepository.findByConfirmationNumber(confirmationNumber);
    
            if (reservation.isPresent()) {
                reservationRepository.delete(reservation.get());
                return true;
            } else {
                return false;
            }
        }

    private String generateUniqueConfirmationNumber() {
        String confirmationNumber;
        int attempts = 0;
        do {
            confirmationNumber = String.format("C%06d", random.nextInt(1_000_000));
            attempts++;
            if (attempts > 10) {
                throw new IllegalStateException("No se pudo generar un número de confirmación único después de 10 intentos.");
            }
        } while (reservationRepository.existsByConfirmationNumber(confirmationNumber));
        return confirmationNumber;
    }

    public List<Reservation> getReservationsByRoom(Long roomId) {
        return reservationRepository.findByRoom_Id(roomId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public boolean canReserveRoom(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return reservationRepository.countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
                roomId, checkOutDate, checkInDate) == 0;
    }

    public boolean canReserveRoom(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Long reservationIdToExclude) {
        return reservationRepository.countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThanAndIdNot(
                roomId, checkOutDate, checkInDate, reservationIdToExclude) == 0;
    }

    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        List<Room> allRooms = roomRepository.findAll();
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : allRooms) {
            boolean isAvailable = canReserveRoom(room.getId(), checkInDate, checkOutDate);
            if (isAvailable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Optional<Reservation> findByConfirmationNumber(String confirmationNumber) {
        return reservationRepository.findByConfirmationNumber(confirmationNumber);
    }
}
