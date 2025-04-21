package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.entity.TypeCostumer;
import ru.imsit.diplom.docmen.filter.TypeCostumerFilter;
import ru.imsit.diplom.docmen.mapper.TypeCostumerMapper;
import ru.imsit.diplom.docmen.repository.DepartmentsRepository;
import ru.imsit.diplom.docmen.repository.TypeCostumerRepository;

import java.io.IOException;
import java.util.UUID;

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

    public TypeCostumerDto getOne(UUID id) {
       return typeCostumerMapper.toTypeCostumerDto(typeCostumerRepository.findById(id).orElseThrow(() -> new RuntimeException("Тип сотрудника не найден")));
    }

    public TypeCostumerDto create(String name, String departments) {
        var department = departmentsRepository.findByName(departments).orElseThrow(() -> new RuntimeException("Департамент не найден"));
        var typeCostumer = TypeCostumer.builder().name(name).department(department).build();
        return typeCostumerMapper.toTypeCostumerDto(typeCostumerRepository.save(typeCostumer));
    }

    public TypeCostumerDto patch(UUID id, String name) throws IOException {
        var typeCostumer = typeCostumerRepository.findById(id).orElseThrow(() -> new RuntimeException("Тип сотрудника не найден"));
        typeCostumer.setName(name);
        return typeCostumerMapper.toTypeCostumerDto(typeCostumerRepository.save(typeCostumer));
    }

    public TypeCostumerDto delete(UUID id) {
        TypeCostumer typeCostumer = typeCostumerRepository.findById(id).orElseThrow(() -> new RuntimeException("Тип сотрудника не найден"));
        if (typeCostumer != null) {
            typeCostumerRepository.delete(typeCostumer);
        }
        return typeCostumerMapper.toTypeCostumerDto(typeCostumer);
    }

}
