package dev.hugo.hotel_management_backend.repository;

import java.util.List;
import java.util.Optional;

import dev.hugo.hotel_management_backend.model.Role;
import dev.hugo.hotel_management_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    List<User> findByRolesIn(List<Role> role);
}
