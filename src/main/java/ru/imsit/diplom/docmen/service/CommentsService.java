package ru.imsit.diplom.docmen.service;

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
import ru.imsit.diplom.docmen.filter.CommentsFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.CommentsMapper;
import ru.imsit.diplom.docmen.repository.CommentsRepository;
import ru.imsit.diplom.docmen.repository.DocCardRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final UserInfoHelper userInfoHelper;

    private final CommentsMapper commentsMapper;

    private final CommentsRepository commentsRepository;

    private final DocCardRepository docCardRepository;

    private final ObjectMapper objectMapper;

    public Page<CommentsDto> getAll(CommentsFilter filter, Pageable pageable) {
        Specification<Comments> spec = filter.toSpecification();
        Page<Comments> comments = commentsRepository.findAll(spec, pageable);
        return comments.map(commentsMapper::toCommentsDto);
    }

    public CommentsDto getOne(String username) {
        Optional<Comments> commentsOptional = commentsRepository.findByUser_Username(username);
        return commentsMapper.toCommentsDto(commentsOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(username))));
    }

    public CommentsDto create(String content, String docCard) {
        var user = userInfoHelper.getUser();
        var docCards = docCardRepository.findByName(docCard);
        var comment = Comments.builder().content(content).docCard(docCards.orElseThrow()).user(user).build();
        return commentsMapper.toCommentsDto(commentsRepository.save(comment));
    }

    public CommentsDto patch(String username, String content, String docCard) throws IOException {
       var docCards = docCardRepository.findByName(docCard);
       var comments = commentsRepository.findByUser_Username(username);
       comments.ifPresent(value -> value.setContent(content));
       comments.ifPresent(value -> value.setDocCard(docCards.orElseThrow()));
       return commentsMapper.toCommentsDto(commentsRepository.save(comments.orElseThrow()));
    }

    public CommentsDto delete(String username) {
        Comments comments = commentsRepository.findByUser_Username(username).orElse(null);
        if (comments != null) {
            commentsRepository.delete(comments);
        }
        return commentsMapper.toCommentsDto(comments);
    }
}
