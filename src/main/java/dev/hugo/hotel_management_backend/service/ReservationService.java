package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Reservation;
import dev.hugo.hotel_management_backend.model.Room; // Asegúrate de importar la clase Room
import dev.hugo.hotel_management_backend.repository.ReservationRepository;
import dev.hugo.hotel_management_backend.repository.RoomRepository; // Importa RoomRepository
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository; // Declara RoomRepository

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository; // Inicializa RoomRepository
    }

    public Reservation createReservation(Reservation reservation) {
        if (!canReserveRoom(reservation.getRoomId(), reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            throw new IllegalArgumentException("La habitación no está disponible en esas fechas.");
        }
        return reservationRepository.save(reservation);
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
