package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.entity.TypeCostumer;
import ru.imsit.diplom.docmen.filtr.TypeCostumerFilter;
import ru.imsit.diplom.docmen.mapper.TypeCostumerMapper;
import ru.imsit.diplom.docmen.repository.DepartmentsRepository;
import ru.imsit.diplom.docmen.repository.TypeCostumerRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TypeCostumerService {

    private final TypeCostumerMapper typeCostumerMapper;

    private final DepartmentsRepository departmentsRepository;

    private final TypeCostumerRepository typeCostumerRepository;

    public Page<TypeCostumerDto> getAll(TypeCostumerFilter filter, Pageable pageable) {
        Specification<TypeCostumer> spec = filter.toSpecification();
        Page<TypeCostumer> typeCostumers = typeCostumerRepository.findAll(spec, pageable);
        return typeCostumers.map(typeCostumerMapper::toTypeCostumerDto);
    }

    public TypeCostumerDto getOne(String name) {
        Optional<TypeCostumer> typeCostumerOptional = typeCostumerRepository.findByName(name);
        return typeCostumerMapper.toTypeCostumerDto(typeCostumerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(name))));
    }

    public TypeCostumerDto create(String name, String departments) {
        var typeCostumer = new TypeCostumer();
        var department = departmentsRepository.findByName(departments);
        typeCostumer = TypeCostumer.builder().name(name).departments(department.orElseThrow()).build();
        return typeCostumerMapper.toTypeCostumerDto(typeCostumerRepository.save(typeCostumer));
    }

    public TypeCostumerDto patch(String name, String changeName) throws IOException {
        var typeCostumer = typeCostumerRepository.findByName(name);
        typeCostumer.ifPresent(u -> u.setName(changeName));
        return typeCostumerMapper.toTypeCostumerDto(typeCostumerRepository.save(typeCostumer.orElseThrow()));
    }

    public TypeCostumerDto delete(String name) {
        TypeCostumer typeCostumer = typeCostumerRepository.findByName(name).orElse(null);
        if (typeCostumer != null) {
            typeCostumerRepository.delete(typeCostumer);
        }
        return typeCostumerMapper.toTypeCostumerDto(typeCostumer);
    }

}
