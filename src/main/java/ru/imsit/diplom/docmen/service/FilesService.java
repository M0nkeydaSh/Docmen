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
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.entity.Files;
import ru.imsit.diplom.docmen.filtr.FilesFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.FilesMapper;
import ru.imsit.diplom.docmen.repository.FilesRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FilesService {

    private final UserInfoHelper userInfoHelper;

    private final FilesMapper filesMapper;

    private final FilesRepository filesRepository;

    private final ObjectMapper objectMapper;

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

    public List<FilesDto> getMany(List<UUID> ids) {
        List<Files> files = filesRepository.findAllById(ids);
        return files.stream()
                .map(filesMapper::toFilesDto)
                .toList();
    }

    public FilesDto create(FilesDto dto) {
        Files files = filesMapper.toEntity(dto);
        files.setUser(userInfoHelper.getUser());
        Files resultFiles = filesRepository.save(files);
        return filesMapper.toFilesDto(resultFiles);
    }

    public FilesDto patch(UUID id, JsonNode patchNode) throws IOException {
        Files files = filesRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        FilesDto filesDto = filesMapper.toFilesDto(files);
        objectMapper.readerForUpdating(filesDto).readValue(patchNode);
        filesMapper.updateWithNull(filesDto, files);

        Files resultFiles = filesRepository.save(files);
        return filesMapper.toFilesDto(resultFiles);
    }

    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<Files> files = filesRepository.findAllById(ids);

        for (Files file : files) {
            FilesDto filesDto = filesMapper.toFilesDto(file);
            objectMapper.readerForUpdating(filesDto).readValue(patchNode);
            filesMapper.updateWithNull(filesDto, file);
        }

        List<Files> resultFiles = filesRepository.saveAll(files);
        return resultFiles.stream()
                .map(Files::getId)
                .toList();
    }

    public FilesDto delete(UUID id) {
        Files files = filesRepository.findById(id).orElse(null);
        if (files != null) {
            filesRepository.delete(files);
        }
        return filesMapper.toFilesDto(files);
    }

    public void deleteMany(List<UUID> ids) {
        filesRepository.deleteAllById(ids);
    }
}
