package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.Departments;

import java.util.UUID;

public interface DepartmentsRepository extends JpaRepository<Departments, UUID>, JpaSpecificationExecutor<Departments> {
}