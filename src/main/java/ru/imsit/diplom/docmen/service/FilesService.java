package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.entity.Files;
import ru.imsit.diplom.docmen.filter.FilesFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.FilesMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.FilesRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FilesService {

    private final UserInfoHelper userInfoHelper;

    private final FilesMapper filesMapper;

    private final FilesRepository filesRepository;

    private final DocCardRepository docCardRepository;

    public Page<FilesDto> getAll(FilesFilter filter, Pageable pageable) {
        Specification<Files> spec = filter.toSpecification();
        Page<Files> files = filesRepository.findAll(spec, pageable);
        return files.map(filesMapper::toFilesDto);
    }

    public FilesDto getOne(UUID id) {
        Optional<Files> filesOptional = filesRepository.findById(id);
        return filesMapper.toFilesDto(filesOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public FilesDto create(String name, String docCard) {
        var user = userInfoHelper.getUser();
        var docCards = docCardRepository.findByName(docCard);
        var file = Files.builder().name(name).docCard(docCards.orElseThrow()).user(user).build();
        return filesMapper.toFilesDto(filesRepository.save(file));
    }

    public FilesDto patch(UUID id, String name) throws IOException {
        var Files = filesRepository.findById(id);
        Files.ifPresent(value -> value.setName(name));
        return filesMapper.toFilesDto(filesRepository.save(Files.orElseThrow()));
    }

    public FilesDto delete(UUID id) {
        Files files = filesRepository.findById(id).orElse(null);
        if (files != null) {
            filesRepository.delete(files);
        }
        return filesMapper.toFilesDto(files);
    }


}
