package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.entity.Departments;
import ru.imsit.diplom.docmen.filter.DepartmentsFilter;
import ru.imsit.diplom.docmen.mapper.DepartmentsMapper;
import ru.imsit.diplom.docmen.repository.DepartmentsRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepartmentsService {

    private final DepartmentsMapper departmentsMapper;

    private final DepartmentsRepository departmentsRepository;

    public Page<DepartmentsDto> getAll(DepartmentsFilter filter, Pageable pageable) {
        Specification<Departments> spec = filter.toSpecification();
        Page<Departments> departments = departmentsRepository.findAll(spec, pageable);
        return departments.map(departmentsMapper::toDepartmentsDto);
    }

    public DepartmentsDto getOne(UUID id) {
        Optional<Departments> departmentsOptional = departmentsRepository.findById(id);
        return departmentsMapper.toDepartmentsDto(departmentsOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public DepartmentsDto create(String name) {
        return departmentsMapper.toDepartmentsDto(departmentsRepository.save(Departments.builder().name(name).build()));
    }

    public DepartmentsDto patch(UUID id, String name) throws IOException {
        var department = departmentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Департамент не найден"));
        department.setName(name);
        department.setChangeDate(LocalDateTime.now());
        return departmentsMapper.toDepartmentsDto(departmentsRepository.save(department));
    }

    public DepartmentsDto delete(UUID id) {
        Departments departments = departmentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Департамент не найден"));
        if (departments != null) {
            departmentsRepository.delete(departments);
        }
        return departmentsMapper.toDepartmentsDto(departments);
    }
}

