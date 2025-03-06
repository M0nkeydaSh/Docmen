package ru.imsit.diplom.docmen.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepartmentsService {

    private final DepartmentsMapper departmentsMapper;

    private final DepartmentsRepository departmentsRepository;

    private final ObjectMapper objectMapper;

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

    public List<DepartmentsDto> getMany(List<UUID> ids) {
        List<Departments> departments = departmentsRepository.findAllById(ids);
        return departments.stream()
                .map(departmentsMapper::toDepartmentsDto)
                .toList();
    }

    public DepartmentsDto create(DepartmentsDto dto) {
        Departments departments = departmentsMapper.toEntity(dto);
        Departments resultDepartments = departmentsRepository.save(departments);
        return departmentsMapper.toDepartmentsDto(resultDepartments);
    }

    public DepartmentsDto patch(UUID id, JsonNode patchNode) throws IOException {
        Departments departments = departmentsRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        DepartmentsDto departmentsDto = departmentsMapper.toDepartmentsDto(departments);
        objectMapper.readerForUpdating(departmentsDto).readValue(patchNode);
        departmentsMapper.updateWithNull(departmentsDto, departments);

        Departments resultDepartments = departmentsRepository.save(departments);
        return departmentsMapper.toDepartmentsDto(resultDepartments);
    }

    public DepartmentsDto delete(UUID id) {
        Departments departments = departmentsRepository.findById(id).orElse(null);
        if (departments != null) {
            departmentsRepository.delete(departments);
        }
        return departmentsMapper.toDepartmentsDto(departments);
    }

    public void deleteMany(List<UUID> ids) {
        departmentsRepository.deleteAllById(ids);
    }
}
