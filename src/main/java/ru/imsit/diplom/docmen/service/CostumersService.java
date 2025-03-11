package ru.imsit.diplom.docmen.service;

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
import ru.imsit.diplom.docmen.repository.TypeCostumerRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CostumersService {

    private final UserInfoHelper userInfoHelper;

    private final TypeCostumerRepository typeCostumerRepository;

    private final CostumersMapper costumersMapper;

    private final CostumersRepository costumersRepository;

    private final ObjectMapper objectMapper;

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

    public CostumersDto create(String firstname, String surName, String lastName, String email, String phoneNumber,String gender, String typeCostumer, String username) {
        var costumers = new Costumers();
        var typeCostumers = typeCostumerRepository.findByName(typeCostumer);
        var user = userInfoHelper.getUserByUsername(username);
        costumers = Costumers.builder().firstName(firstname).surName(surName).lastName(lastName).email(email).phoneNumber(phoneNumber).gender(gender).typeCostumer(typeCostumers.orElseThrow()).user(user).build();
        return costumersMapper.toCostumersDto(costumersRepository.save(costumers));
    }

    public CostumersDto patch(String username, String firstname, String surName, String lastName, String email, String phoneNumber,String gender, String typeCostumer) {
        var costumer = costumersRepository.findByUser_Username(username);
        var typeCostumers = typeCostumerRepository.findByName(typeCostumer);
        var user = userInfoHelper.getUserByUsername(username);
        costumer.ifPresent(costumer1 -> costumer1.setFirstName(firstname));
        costumer.ifPresent(costumer1 -> costumer1.setSurName(surName));
        costumer.ifPresent(costumer1 -> costumer1.setLastName(lastName));
        costumer.ifPresent(costumer1 -> costumer1.setEmail(email));
        costumer.ifPresent(costumer1 -> costumer1.setPhoneNumber(phoneNumber));
        costumer.ifPresent(costumer1 -> costumer1.setGender(gender));
        costumer.ifPresent(costumer1 -> costumer1.setTypeCostumer(typeCostumers.orElseThrow()));
        costumer.ifPresent(costumer1 -> costumer1.setUser(user));
        return costumersMapper.toCostumersDto(costumersRepository.save(costumer.orElseThrow()));
    }

    public CostumersDto delete(String username) {
        Costumers costumers = costumersRepository.findByUser_Username(username).orElse(null);
        if (costumers != null) {
            costumersRepository.delete(costumers);
        }
        return costumersMapper.toCostumersDto(costumers);
    }

}
