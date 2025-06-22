package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.entity.Files;
import ru.imsit.diplom.docmen.filter.FilesFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.FilesMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.FilesRepository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    public FilesDto create(String name, String docCardId) {
        var user = userInfoHelper.getUser();
        var docCard = docCardRepository.findById(UUID.fromString(docCardId)).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        var file = Files.builder().name(name).docCard(docCard).user(user).build();
        return filesMapper.toFilesDto(filesRepository.save(file));
    }

    public FilesDto patch(UUID id, String name) throws IOException {
        var file = filesRepository.findById(id).orElseThrow(() -> new RuntimeException("Файл не найден"));
        file.setName(name);
        file.setChangeDate(LocalDateTime.now());
        return filesMapper.toFilesDto(filesRepository.save(file));
    }

    public FilesDto delete(UUID id) {
        Files file = filesRepository.findById(id).orElseThrow(() -> new RuntimeException("Файл не найден"));
        if (file != null) {
            filesRepository.delete(file);
        }
        return filesMapper.toFilesDto(file);
    }

    /**
     * Загрузка файла на сервер
     *
     * @param docCardId - название карточки документа
     * @param file      - файл, который нужно загрузить на сервер
     */
    public void upload(String docCardId, MultipartFile file) {
        store(file, docCardId);
        create(file.getOriginalFilename(), docCardId);
    }

    private void store(MultipartFile file, String docCardId) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Ошибка: Файл пустой. Пожалуйста, выберите файл для загрузки.");
            }

            // Получить текущую директорию проекта
            Path currentDir = Paths.get("").toAbsolutePath();

            // Получить абсолютный путь до директории проекта
            Path filesRootDir = Paths.get(currentDir.toString(), "files");

            // Получить абсолютный путь до директории для хранения файлов
            Path absolutePath = Paths.get(filesRootDir.toString(), docCardId);

            // Создать директорию, если она не существует
            if (!java.nio.file.Files.exists(absolutePath)) {
                java.nio.file.Files.createDirectories(absolutePath);
            }

            // Сохранить файл в директории проекта
            Path destinationFile = Paths.get(absolutePath.toString(), file.getOriginalFilename());

            try (InputStream inputStream = file.getInputStream()) {
                java.nio.file.Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла", e);
        }
    }

    // Downloading a file
    public ResponseEntity<?> downloadFile(String filename, String docCardId) throws FileNotFoundException {

        // Проверяем, существует ли файл
        String fileUploadPath = Paths.get("").toAbsolutePath() + File.separator + "files" + File.separator + docCardId;
        String[] filenames = this.getFiles(docCardId);
        boolean contains = Arrays.asList(filenames).contains(filename);
        if (!contains) {
            return new ResponseEntity<>("FIle Not Found", HttpStatus.NOT_FOUND);
        }

        // Устанавливаем абсолютный путь к файлу на сервере
        String filePath = fileUploadPath + File.separator + filename;

        // Создаем объект типа File, который будет представлять файл на сервере
        File file = new File(filePath);

        // Создаем объект InputStreamResource, который будет использоваться для передачи файла клиенту
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // Устанавливаем заголовки для ответа HTTP
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=" + filename;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }

    private String[] getFiles(String docCardId) {
        String folderPath = Paths.get("").toAbsolutePath() + File.separator + "files" + File.separator + docCardId + File.separator;

        // Создаём объект типа File, который будет представлять каталог
        File directory = new File(folderPath);

        // list() метод возвращает массив файлов и каталогов
        return directory.list();

    }

}
