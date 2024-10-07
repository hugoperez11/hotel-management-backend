package dev.hugo.hotel_management_backend.repository;

import dev.hugo.hotel_management_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
