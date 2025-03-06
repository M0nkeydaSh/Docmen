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
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.entity.Comments;
import ru.imsit.diplom.docmen.filtr.CommentsFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.CommentsMapper;
import ru.imsit.diplom.docmen.repository.CommentsRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final UserInfoHelper userInfoHelper;

    private final CommentsMapper commentsMapper;

    private final CommentsRepository commentsRepository;

    private final ObjectMapper objectMapper;

    public Page<CommentsDto> getAll(CommentsFilter filter, Pageable pageable) {
        Specification<Comments> spec = filter.toSpecification();
        Page<Comments> comments = commentsRepository.findAll(spec, pageable);
        return comments.map(commentsMapper::toCommentsDto);
    }

    public CommentsDto getOne(UUID id) {
        Optional<Comments> commentsOptional = commentsRepository.findById(id);
        return commentsMapper.toCommentsDto(commentsOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<CommentsDto> getMany(List<UUID> ids) {
        List<Comments> comments = commentsRepository.findAllById(ids);
        return comments.stream()
                .map(commentsMapper::toCommentsDto)
                .toList();
    }

    public CommentsDto create(CommentsDto dto) {
        Comments comments = commentsMapper.toEntity(dto);
        comments.setUser(userInfoHelper.getUser());
        Comments resultComments = commentsRepository.save(comments);
        return commentsMapper.toCommentsDto(resultComments);
    }

    public CommentsDto patch(UUID id, JsonNode patchNode) throws IOException {
        Comments comments = commentsRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        CommentsDto commentsDto = commentsMapper.toCommentsDto(comments);
        objectMapper.readerForUpdating(commentsDto).readValue(patchNode);
        commentsMapper.updateWithNull(commentsDto, comments);

        Comments resultComments = commentsRepository.save(comments);
        return commentsMapper.toCommentsDto(resultComments);
    }

    public CommentsDto delete(UUID id) {
        Comments comments = commentsRepository.findById(id).orElse(null);
        if (comments != null) {
            commentsRepository.delete(comments);
        }
        return commentsMapper.toCommentsDto(comments);
    }

    public void deleteMany(List<UUID> ids) {
        commentsRepository.deleteAllById(ids);
    }
}
