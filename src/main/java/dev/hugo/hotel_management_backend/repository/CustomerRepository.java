package dev.hugo.hotel_management_backend.repository;

import dev.hugo.hotel_management_backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Método para encontrar un cliente por su correo
    Customer findByEmail(String email);
}


