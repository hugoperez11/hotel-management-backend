package dev.hugo.hotel_management_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.hugo.hotel_management_backend.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can define custom queries here if needed
}

