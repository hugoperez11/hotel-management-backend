package dev.hugo.hotel_management_backend.configtest;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    private final String endpoint = "/api/v1"; // Change this to match your actual endpoint

    @BeforeEach
    public void setUp() {
        // Any setup can be done here if needed
    }

   

    @Test
    public void testPermitAllForImages() throws Exception {
        mockMvc.perform(get("/img/Suite.jpg")) 
                .andExpect(status().isOk());
    }

    @Test
    public void testPermitAllForRoomGet() throws Exception {
        mockMvc.perform(get(endpoint + "/rooms/1")) 
                .andExpect(status().isOk());
    }

  

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccessForLogin() throws Exception {
        mockMvc.perform(get(endpoint + "/login"))
                .andExpect(status().isAccepted()); 
    }

    @Test
    public void testPermitAllForReservationConfirmationGet() throws Exception {
        mockMvc.perform(get(endpoint + "/reservations/confirmation/C123456"))
                .andExpect(status().isOk());
    }

   

    @Test
    public void testPermitAllForReservationConfirmationDelete() throws Exception {
        mockMvc.perform(get(endpoint + "/reservations/confirmation/C123456")) // Replace with a valid confirmation number
                .andExpect(status().isOk());
    }

    @Test
    public void testUnauthorizedAccessForProtectedResources() throws Exception {
        mockMvc.perform(get(endpoint + "/protected/resource")) // Replace with an actual protected resource
                .andExpect(status().isUnauthorized());
    }

}

