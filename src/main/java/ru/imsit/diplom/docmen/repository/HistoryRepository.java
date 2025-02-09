package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.imsit.diplom.docmen.entity.History;

import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
}