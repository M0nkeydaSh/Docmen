package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.entity.Comments;
import ru.imsit.diplom.docmen.filter.CommentsFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.CommentsMapper;
import ru.imsit.diplom.docmen.repository.CommentsRepository;
import ru.imsit.diplom.docmen.repository.DocCardRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final UserInfoHelper userInfoHelper;

    private final CommentsMapper commentsMapper;

    private final CommentsRepository commentsRepository;

    private final DocCardRepository docCardRepository;

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

    public CommentsDto create(String content, String docCardId) {
        var user = userInfoHelper.getUser();
        var docCards = docCardRepository.findById(UUID.fromString(docCardId)).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        var comment = Comments.builder().content(content).docCard(docCards).user(user).build();
        return commentsMapper.toCommentsDto(commentsRepository.save(comment));
    }

    public CommentsDto patch(UUID id, String content) throws IOException {
        var comments = commentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        comments.setContent(content);
        return commentsMapper.toCommentsDto(commentsRepository.save(comments));
    }

    public CommentsDto delete(UUID id) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        if (comments != null) {
            commentsRepository.delete(comments);
        }
        return commentsMapper.toCommentsDto(comments);
    }
}
