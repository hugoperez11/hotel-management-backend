package dev.hugo.hotel_management_backend.service;

import dev.hugo.hotel_management_backend.model.Room;
import dev.hugo.hotel_management_backend.repository.ReservationRepository;
import dev.hugo.hotel_management_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomService(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow();
    }

    public List<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        List<Room> allRooms = roomRepository.findAll();

        return allRooms.stream()
            .filter(room -> reservationRepository
                .countByRoom_IdAndCheckInDateLessThanAndCheckOutDateGreaterThan(
                    room.getId(), checkOut, checkIn) == 0) // Cambiado a `countByRoomIdAndCheckInDateLessThanAndCheckOutDateGreaterThan`
            .collect(Collectors.toList());
    }
}
