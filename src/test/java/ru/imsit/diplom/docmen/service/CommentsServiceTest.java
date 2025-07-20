package ru.imsit.diplom.docmen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.dto.TypeDocumentDto;
import ru.imsit.diplom.docmen.entity.Comments;
import ru.imsit.diplom.docmen.entity.DocCard;
import ru.imsit.diplom.docmen.entity.User;
import ru.imsit.diplom.docmen.filter.CommentsFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.CommentsMapper;
import ru.imsit.diplom.docmen.repository.CommentsRepository;
import ru.imsit.diplom.docmen.repository.DocCardRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest {

    @InjectMocks
    private CommentsService commentsService;

    @Mock
    private UserInfoHelper userInfoHelper;

    @Mock
    private CommentsMapper commentsMapper;

    @Mock
    private CommentsRepository commentsRepository;

    @Mock
    private DocCardRepository docCardRepository;

    private final UUID testId = UUID.randomUUID();
    private final String testContent = "Test content";
    private final String testDocCardId = UUID.randomUUID().toString();
    private Comments testComment;
    private CommentsDto testCommentDto;

    @BeforeEach
    void setUp() {
        testComment = Comments.builder()
                .user(User.builder().username("John Doe").build())
                .docCard(DocCard.builder().name("Test Document").build())
                .content(testContent)
                .build();

        testCommentDto = createCommentDto();
    }

    @Test
    void getAll_ReturnsPagedComments() {
        // Arrange
        CommentsFilter filter = createCommentsFilter();
        Pageable pageable = PageRequest.of(0, 10);
        List<Comments> commentsList = List.of(testComment, testComment);
        Page<Comments> commentsPage = new PageImpl<>(commentsList);

        when(commentsRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(commentsPage);
        when(commentsMapper.toCommentsDto(testComment)).thenReturn(testCommentDto);

        // Act
        Page<CommentsDto> result = commentsService.getAll(filter, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(commentsRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(commentsMapper, times(2)).toCommentsDto(testComment);
    }

    @Test
    void getOne_ReturnsExistingComment() {
        // Arrange
        when(commentsRepository.findById(testId)).thenReturn(Optional.of(testComment));
        when(commentsMapper.toCommentsDto(testComment)).thenReturn(testCommentDto);

        // Act
        CommentsDto result = commentsService.getOne(testId);

        // Assert
        assertNotNull(result);
        assertEquals(testContent, result.getContent());
        verify(commentsRepository, times(1)).findById(testId);
        verify(commentsMapper, times(1)).toCommentsDto(testComment);
    }

    @Test
    void getOne_ThrowsExceptionWhenNotFound() {
        // Arrange
        when(commentsRepository.findById(testId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> commentsService.getOne(testId));
        verify(commentsRepository, times(1)).findById(testId);
    }

    @Test
    void create_ReturnsCreatedComment() {
        // Arrange
        var user = User.builder().username("John Doe").build();
        var docCard = DocCard.builder().name("Test Document").build();

        when(userInfoHelper.getUser()).thenReturn(user);
        when(docCardRepository.findById(UUID.fromString(testDocCardId))).thenReturn(Optional.of(docCard));
        when(commentsRepository.save(any(Comments.class))).thenReturn(testComment);
        when(commentsMapper.toCommentsDto(testComment)).thenReturn(testCommentDto);

        // Act
        CommentsDto result = commentsService.create(testContent, testDocCardId);

        // Assert
        assertNotNull(result);
        assertEquals(testContent, result.getContent());
        verify(userInfoHelper, times(1)).getUser();
        verify(docCardRepository, times(1)).findById(UUID.fromString(testDocCardId));
        verify(commentsRepository, times(1)).save(any(Comments.class));
        verify(commentsMapper, times(1)).toCommentsDto(testComment);
    }

    @Test
    void create_ThrowsExceptionWhenDocCardNotFound() {
        // Arrange
        when(docCardRepository.findById(UUID.fromString(testDocCardId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentsService.create(testContent, testDocCardId));
        verify(docCardRepository, times(1)).findById(UUID.fromString(testDocCardId));
    }

    @Test
    void patch_ReturnsUpdatedComment() throws IOException {
        // Arrange
        when(commentsRepository.findById(testId)).thenReturn(Optional.of(testComment));
        when(commentsRepository.save(any(Comments.class))).thenReturn(testComment);
        testCommentDto.setContent("New content");
        when(commentsMapper.toCommentsDto(testComment)).thenReturn(testCommentDto);

        // Act
        CommentsDto result = commentsService.patch(testId, "New content");

        // Assert
        assertNotNull(result);
        assertEquals("New content", result.getContent());
        verify(commentsRepository, times(1)).findById(testId);
        verify(commentsRepository, times(1)).save(any(Comments.class));
        verify(commentsMapper, times(1)).toCommentsDto(testComment);
    }

    @Test
    void patch_ThrowsExceptionWhenCommentNotFound() {
        // Arrange
        when(commentsRepository.findById(testId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentsService.patch(testId, "New content"));
        verify(commentsRepository, times(1)).findById(testId);
    }

    @Test
    void delete_ReturnsDeletedComment() {
        // Arrange
        when(commentsRepository.findById(testId)).thenReturn(Optional.of(testComment));
        when(commentsMapper.toCommentsDto(testComment)).thenReturn(testCommentDto);

        // Act
        CommentsDto result = commentsService.delete(testId);

        // Assert
        assertNotNull(result);
        assertEquals(testContent, result.getContent());
        verify(commentsRepository, times(1)).findById(testId);
        verify(commentsMapper, times(1)).toCommentsDto(testComment);
        verify(commentsRepository, times(1)).delete(testComment);
    }

    @Test
    void delete_ThrowsExceptionWhenCommentNotFound() {
        // Arrange
        when(commentsRepository.findById(testId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentsService.delete(testId));
        verify(commentsRepository, times(1)).findById(testId);
    }

    private CommentsDto createCommentDto() {
        return new CommentsDto(
                "Test content",
                "Test username",
                new DocCardDto(
                        "Test docCardId",
                        "Test title",
                        "Test description",
                        "Test status",
                        new TypeDocumentDto(
                                "Test typeDocumentName"
                        ),
                        "123456789",
                        "Test keyWords",
                        "Черновик"));
    }

    private CommentsFilter createCommentsFilter() {
        return new CommentsFilter(
                "Test docCardId"
        );
    }
}
