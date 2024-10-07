package dev.hugo.hotel_management_backend.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void testGettersAndSetters() {
        Reservation reservation = new Reservation();

        // Create instances of Room and Customer for testing
        Room room = new Room();
        room.setId(1L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        LocalDate checkInDate = LocalDate.of(2024, 10, 10);
        LocalDate checkOutDate = LocalDate.of(2024, 10, 15);
        String confirmationNumber = "CONF12345";

        // Set values
        reservation.setId(1L);
        reservation.setRoom(room);
        reservation.setCustomer(customer);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setConfirmationNumber(confirmationNumber);

        // Assertions
        assertEquals(1L, reservation.getId());
        assertEquals(room, reservation.getRoom());
        assertEquals(customer, reservation.getCustomer());
        assertEquals(checkInDate, reservation.getCheckInDate());
        assertEquals(checkOutDate, reservation.getCheckOutDate());
        assertEquals(confirmationNumber, reservation.getConfirmationNumber());
    }

    @Test
    void testGetRoomId() {
        Reservation reservation = new Reservation();
        Room room = new Room();
        room.setId(1L);
        reservation.setRoom(room);

        assertEquals(1L, reservation.getRoomId());

        // Test with null room
        reservation.setRoom(null);
        assertNull(reservation.getRoomId());
    }
}

