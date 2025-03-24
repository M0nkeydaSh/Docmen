package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.TypeDocument;

import java.util.Optional;
import java.util.UUID;

public interface TypeDocumentRepository extends JpaRepository<TypeDocument, UUID>, JpaSpecificationExecutor<TypeDocument> {

    Optional<TypeDocument> findByName(String name);

}