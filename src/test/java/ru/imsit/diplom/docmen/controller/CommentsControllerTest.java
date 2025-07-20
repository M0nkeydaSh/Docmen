package ru.imsit.diplom.docmen.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.dto.TypeDocumentDto;
import ru.imsit.diplom.docmen.filter.CommentsFilter;
import ru.imsit.diplom.docmen.service.CommentsService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentsControllerTest {

    @Mock
    private CommentsService commentsService;

    @InjectMocks
    private CommentsController commentsController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void AfterEach() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void getAll_ReturnsPagedModel() {
        // Arrange
        CommentsFilter filter = createCommentsFilter();
        Pageable pageable = PageRequest.of(0, 10);
        List<CommentsDto> commentsList = List.of(
                createCommentDto(),
                createCommentDto()
        );
        Page<CommentsDto> commentsPage = new PageImpl<>(commentsList);

        when(commentsService.getAll(filter, pageable)).thenReturn(commentsPage);

        // Act
        PagedModel<CommentsDto> result = commentsController.getAll(filter, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(commentsService, times(1)).getAll(filter, pageable);
    }

    @Test
    void getOne_ReturnsComment() {
        // Arrange
        UUID id = UUID.randomUUID();
        CommentsDto comment = createCommentDto();

        when(commentsService.getOne(id)).thenReturn(comment);

        // Act
        CommentsDto result = commentsController.getOne(id);

        // Assert
        assertNotNull(result);
        verify(commentsService, times(1)).getOne(id);
    }

    @Test
    void create_ReturnsCreatedComment() {
        // Arrange
        String content = "Test content";
        String docCardId = "12345";
        CommentsDto createdComment = createCommentDto();

        when(commentsService.create(content, docCardId)).thenReturn(createdComment);

        // Act
        CommentsDto result = commentsController.create(content, docCardId);

        // Assert
        assertNotNull(result);
        verify(commentsService, times(1)).create(content, docCardId);
    }

    @Test
    void patch_ReturnsUpdatedComment() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        String content = "Updated content";
        CommentsDto updatedComment = createCommentDto();

        when(commentsService.patch(id, content)).thenReturn(updatedComment);

        // Act
        CommentsDto result = commentsController.patch(id, content);

        // Assert
        assertNotNull(result);
        verify(commentsService, times(1)).patch(id, content);
    }

    @Test
    void delete_ReturnsDeletedComment() {
        // Arrange
        UUID id = UUID.randomUUID();
        CommentsDto deletedComment = createCommentDto();

        when(commentsService.delete(id)).thenReturn(deletedComment);

        // Act
        CommentsDto result = commentsController.delete(id);

        // Assert
        assertNotNull(result);
        verify(commentsService, times(1)).delete(id);
    }

    @Test
    void patch_ThrowsIOException() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        String content = "Updated content";
        IOException exception = new IOException("Database error");

        when(commentsService.patch(id, content)).thenThrow(exception);

        // Act & Assert
        assertThrows(IOException.class, () -> commentsController.patch(id, content));
        verify(commentsService, times(1)).patch(id, content);
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
