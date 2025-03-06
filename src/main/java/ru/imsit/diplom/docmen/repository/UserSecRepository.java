package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.imsit.diplom.docmen.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserSecRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameIgnoreCase(String username);
}