package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.Departments;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentsRepository extends JpaRepository<Departments, UUID>, JpaSpecificationExecutor<Departments> {
   // Departments findByNameNoOpt(String name);

    Optional<Departments> findByName(String name);
}