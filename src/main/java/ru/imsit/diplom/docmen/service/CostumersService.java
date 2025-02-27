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
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.entity.Costumers;
import ru.imsit.diplom.docmen.filtr.CostumersFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.CostumersMapper;
import ru.imsit.diplom.docmen.repository.CostumersRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CostumersService {

    private final UserInfoHelper userInfoHelper;

    private final CostumersMapper costumersMapper;

    private final CostumersRepository costumersRepository;

    private final ObjectMapper objectMapper;

    public Page<CostumersDto> getAll(CostumersFilter filter, Pageable pageable) {
        Specification<Costumers> spec = filter.toSpecification();
        Page<Costumers> costumers = costumersRepository.findAll(spec, pageable);
        return costumers.map(costumersMapper::toCostumersDto);
    }

    public CostumersDto getOne(UUID id) {
        Optional<Costumers> costumersOptional = costumersRepository.findById(id);
        return costumersMapper.toCostumersDto(costumersOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<CostumersDto> getMany(List<UUID> ids) {
        List<Costumers> costumers = costumersRepository.findAllById(ids);
        return costumers.stream()
                .map(costumersMapper::toCostumersDto)
                .toList();
    }

    public CostumersDto create(CostumersDto dto) {
        Costumers costumers = costumersMapper.toEntity(dto);
        costumers.setUser(userInfoHelper.getUser());
        Costumers resultCostumers = costumersRepository.save(costumers);
        return costumersMapper.toCostumersDto(resultCostumers);
    }

    public CostumersDto patch(UUID id, JsonNode patchNode) throws IOException {
        Costumers costumers = costumersRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        CostumersDto costumersDto = costumersMapper.toCostumersDto(costumers);
        objectMapper.readerForUpdating(costumersDto).readValue(patchNode);
        costumersMapper.updateWithNull(costumersDto, costumers);

        Costumers resultCostumers = costumersRepository.save(costumers);
        return costumersMapper.toCostumersDto(resultCostumers);
    }

    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<Costumers> costumers = costumersRepository.findAllById(ids);

        for (Costumers costumer : costumers) {
            CostumersDto costumersDto = costumersMapper.toCostumersDto(costumer);
            objectMapper.readerForUpdating(costumersDto).readValue(patchNode);
            costumersMapper.updateWithNull(costumersDto, costumer);
        }

        List<Costumers> resultCostumers = costumersRepository.saveAll(costumers);
        return resultCostumers.stream()
                .map(Costumers::getId)
                .toList();
    }

    public CostumersDto delete(UUID id) {
        Costumers costumers = costumersRepository.findById(id).orElse(null);
        if (costumers != null) {
            costumersRepository.delete(costumers);
        }
        return costumersMapper.toCostumersDto(costumers);
    }

    public void deleteMany(List<UUID> ids) {
        costumersRepository.deleteAllById(ids);
    }
}
