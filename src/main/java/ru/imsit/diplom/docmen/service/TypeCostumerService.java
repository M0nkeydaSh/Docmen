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
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.entity.TypeCostumer;
import ru.imsit.diplom.docmen.filtr.TypeCostumerFilter;
import ru.imsit.diplom.docmen.mapper.TypeCostumerMapper;
import ru.imsit.diplom.docmen.repository.TypeCostumerRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TypeCostumerService {

    private final TypeCostumerMapper typeCostumerMapper;

    private final TypeCostumerRepository typeCostumerRepository;

    private final ObjectMapper objectMapper;

    public Page<TypeCostumerDto> getAll(TypeCostumerFilter filter, Pageable pageable) {
        Specification<TypeCostumer> spec = filter.toSpecification();
        Page<TypeCostumer> typeCostumers = typeCostumerRepository.findAll(spec, pageable);
        return typeCostumers.map(typeCostumerMapper::toTypeCostumerDto);
    }

    public TypeCostumerDto getOne(UUID id) {
        Optional<TypeCostumer> typeCostumerOptional = typeCostumerRepository.findById(id);
        return typeCostumerMapper.toTypeCostumerDto(typeCostumerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<TypeCostumerDto> getMany(List<UUID> ids) {
        List<TypeCostumer> typeCostumers = typeCostumerRepository.findAllById(ids);
        return typeCostumers.stream()
                .map(typeCostumerMapper::toTypeCostumerDto)
                .toList();
    }

    public TypeCostumerDto create(TypeCostumerDto dto) {
        TypeCostumer typeCostumer = typeCostumerMapper.toEntity(dto);
        TypeCostumer resultTypeCostumer = typeCostumerRepository.save(typeCostumer);
        return typeCostumerMapper.toTypeCostumerDto(resultTypeCostumer);
    }

    public TypeCostumerDto patch(UUID id, JsonNode patchNode) throws IOException {
        TypeCostumer typeCostumer = typeCostumerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        TypeCostumerDto typeCostumerDto = typeCostumerMapper.toTypeCostumerDto(typeCostumer);
        objectMapper.readerForUpdating(typeCostumerDto).readValue(patchNode);
        typeCostumerMapper.updateWithNull(typeCostumerDto, typeCostumer);

        TypeCostumer resultTypeCostumer = typeCostumerRepository.save(typeCostumer);
        return typeCostumerMapper.toTypeCostumerDto(resultTypeCostumer);
    }

    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<TypeCostumer> typeCostumers = typeCostumerRepository.findAllById(ids);

        for (TypeCostumer typeCostumer : typeCostumers) {
            TypeCostumerDto typeCostumerDto = typeCostumerMapper.toTypeCostumerDto(typeCostumer);
            objectMapper.readerForUpdating(typeCostumerDto).readValue(patchNode);
            typeCostumerMapper.updateWithNull(typeCostumerDto, typeCostumer);
        }

        List<TypeCostumer> resultTypeCostumers = typeCostumerRepository.saveAll(typeCostumers);
        return resultTypeCostumers.stream()
                .map(TypeCostumer::getId)
                .toList();
    }

    public TypeCostumerDto delete(UUID id) {
        TypeCostumer typeCostumer = typeCostumerRepository.findById(id).orElse(null);
        if (typeCostumer != null) {
            typeCostumerRepository.delete(typeCostumer);
        }
        return typeCostumerMapper.toTypeCostumerDto(typeCostumer);
    }

    public void deleteMany(List<UUID> ids) {
        typeCostumerRepository.deleteAllById(ids);
    }
}
