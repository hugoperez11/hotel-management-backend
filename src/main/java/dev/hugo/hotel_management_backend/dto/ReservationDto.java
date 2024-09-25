package dev.hugo.hotel_management_backend.dto;

import java.time.LocalDate;

public class ReservationDto {
    private Long roomId;        // ID of the room
    private Long customerId;    // ID of the customer
    private LocalDate checkInDate;  // Check-in date
    private LocalDate checkOutDate; // Check-out date


    public Long getRoomId() {
        return roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    
}
