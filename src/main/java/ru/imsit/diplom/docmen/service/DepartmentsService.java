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
import ru.imsit.diplom.docmen.filtr.DepartmentsFilter;
import ru.imsit.diplom.docmen.mapper.DepartmentsMapper;
import ru.imsit.diplom.docmen.repository.DepartmentsRepository;

import java.io.IOException;
import java.util.Optional;

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

    public DepartmentsDto getOne(String name) {
        Optional<Departments> departmentsOptional = departmentsRepository.findByName(name);
        return departmentsMapper.toDepartmentsDto(departmentsOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(name))));
    }

    public DepartmentsDto create(String name) {
        var departments = new Departments();
        departments = Departments.builder().name(name).build();
        return departmentsMapper.toDepartmentsDto(departmentsRepository.save(departments));
    }

    public DepartmentsDto patch(String name, String changeName) throws IOException {
        var department = departmentsRepository.findByName(name);
        department.ifPresent(u -> u.setName(changeName));
        return departmentsMapper.toDepartmentsDto(departmentsRepository.save(department.orElseThrow()));
    }

    public DepartmentsDto delete(String name) {
        Departments departments = departmentsRepository.findByName(name).orElse(null);
        if (departments != null) {
            departmentsRepository.delete(departments);
        }
        return departmentsMapper.toDepartmentsDto(departments);
    }
}

