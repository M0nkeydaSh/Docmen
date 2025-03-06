package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.Files;

import java.util.UUID;

public interface FilesRepository extends JpaRepository<Files, UUID>, JpaSpecificationExecutor<Files> {
}