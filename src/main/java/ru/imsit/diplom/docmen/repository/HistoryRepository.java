package ru.imsit.diplom.docmen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.imsit.diplom.docmen.entity.History;

import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
    Page<History> findAll(Specification<History> spec, Pageable pageable);
}