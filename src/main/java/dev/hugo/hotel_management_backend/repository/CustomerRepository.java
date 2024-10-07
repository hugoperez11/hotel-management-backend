package dev.hugo.hotel_management_backend.repository;

import dev.hugo.hotel_management_backend.model.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNameAndEmail(String name, String email);

}


