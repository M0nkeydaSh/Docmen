package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.entity.Costumers;
import ru.imsit.diplom.docmen.enums.GenderEnum;
import ru.imsit.diplom.docmen.filter.CostumersFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.CostumersMapper;
import ru.imsit.diplom.docmen.repository.CostumersRepository;
import ru.imsit.diplom.docmen.repository.TypeCostumerRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CostumersService {

    private final UserInfoHelper userInfoHelper;

    private final TypeCostumerRepository typeCostumerRepository;

    private final CostumersMapper costumersMapper;

    private final CostumersRepository costumersRepository;

    public Page<CostumersDto> getAll(CostumersFilter filter, Pageable pageable) {
        Specification<Costumers> spec = filter.toSpecification();
        Page<Costumers> costumers = costumersRepository.findAll(spec, pageable);
        return costumers.map(costumersMapper::toCostumersDto);
    }

    public CostumersDto getOne(String username) {
        Optional<Costumers> costumersOptional = costumersRepository.findByUser_Username(username);
        return costumersMapper.toCostumersDto(costumersOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(username))));
    }

    public CostumersDto create(String firstname, String surName, String lastName, String email, String phoneNumber, String gender, String typeCostumer, String username) {
        var typeCostumers = typeCostumerRepository.findByName(typeCostumer).orElseThrow(() -> new RuntimeException("Тип сотрудника не найден"));
        var user = userInfoHelper.getUserByUsername(username);
        var costumers = Costumers.builder()
                .firstName(firstname)
                .surName(surName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .gender(GenderEnum.getGender(gender))
                .typeCostumer(typeCostumers)
                .user(user)
                .build();
        return costumersMapper.toCostumersDto(costumersRepository.save(costumers));
    }

    public CostumersDto patch(String username, String firstname, String surName, String lastName, String email, String phoneNumber, String gender, String typeCostumer) {
        var costumer = costumersRepository.findByUser_Username(username).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        var typeCostumers = typeCostumerRepository.findByName(typeCostumer).orElseThrow(() -> new RuntimeException("Тип сотрудника не найден"));
        var user = userInfoHelper.getUserByUsername(username);
        costumer.setFirstName(firstname);
        costumer.setSurName(surName);
        costumer.setLastName(lastName);
        costumer.setEmail(email);
        costumer.setPhoneNumber(phoneNumber);
        costumer.setGender(GenderEnum.valueOf(gender));
        costumer.setTypeCostumer(typeCostumers);
        costumer.setUser(user);
        return costumersMapper.toCostumersDto(costumersRepository.save(costumer));
    }

    public CostumersDto delete(String username) {
        Costumers costumers = costumersRepository.findByUser_Username(username).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        if (costumers != null) {
            costumersRepository.delete(costumers);
        }
        return costumersMapper.toCostumersDto(costumers);
    }

}
