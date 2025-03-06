package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.DocCard;

import java.util.UUID;

public interface DocCardRepository extends JpaRepository<DocCard, UUID>, JpaSpecificationExecutor<DocCard> {
}