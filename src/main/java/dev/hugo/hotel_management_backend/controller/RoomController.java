package dev.hugo.hotel_management_backend.controller;

import dev.hugo.hotel_management_backend.model.Room;
import dev.hugo.hotel_management_backend.service.RoomService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

@GetMapping("/available")
public List<Room> getAvailableRooms(@RequestParam("checkInDate") String checkInDate,
                                     @RequestParam("checkOutDate") String checkOutDate) {
    try {
        LocalDate checkIn = LocalDate.parse(checkInDate.trim());
        LocalDate checkOut = LocalDate.parse(checkOutDate.trim());
        return roomService.getAvailableRooms(checkIn, checkOut);
    } catch (DateTimeParseException e) {
        // Manejo de error, puedes lanzar una excepción o devolver un mensaje de error
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de fecha inválido");
    }
}


    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }
}
