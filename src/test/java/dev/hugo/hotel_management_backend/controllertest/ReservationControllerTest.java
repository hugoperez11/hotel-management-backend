package dev.hugo.hotel_management_backend.controllertest;

import dev.hugo.hotel_management_backend.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import dev.hugo.hotel_management_backend.controller.ReservationController;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    } 

 

    @Test
    public void testDeleteReservation() throws Exception {
        when(reservationService.deleteReservationByConfirmationNumber("C123456")).thenReturn(true);

        mockMvc.perform(delete("/api/v1/reservations/confirmation/C123456"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reserva eliminada con éxito"));
    }

    @Test
    public void testDeleteReservationNotFound() throws Exception {
        when(reservationService.deleteReservationByConfirmationNumber("C123456")).thenReturn(false);

        mockMvc.perform(delete("/api/v1/reservations/confirmation/C123456"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se encontró la reserva con ese número de confirmación"));
    }
}
