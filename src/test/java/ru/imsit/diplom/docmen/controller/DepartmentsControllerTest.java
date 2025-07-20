package ru.imsit.diplom.docmen.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.filter.DepartmentsFilter;
import ru.imsit.diplom.docmen.service.DepartmentsService;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentsControllerTest {

    @Mock
    private DepartmentsService departmentsService;

    @InjectMocks
    private DepartmentsController departmentsController;

    @Test
    void getAll_WithFilterAndPageable_ReturnsPagedModel() {
        // Arrange
        DepartmentsFilter filter = createDepartmentsFilter();
        Pageable pageable = PageRequest.of(0, 10);
        Page<DepartmentsDto> page = new PageImpl<>(Collections.singletonList(createDepartmentsDto()));

        when(departmentsService.getAll(filter, pageable)).thenReturn(page);

        // Act
        PagedModel<DepartmentsDto> result = departmentsController.getAll(filter, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(departmentsService, times(1)).getAll(filter, pageable);
    }

    @Test
    void getOne_ValidId_ReturnsDepartment() {
        // Arrange
        UUID id = UUID.randomUUID();
        DepartmentsDto dto = createDepartmentsDto();
        when(departmentsService.getOne(id)).thenReturn(dto);

        // Act
        DepartmentsDto result = departmentsController.getOne(id);

        // Assert
        assertNotNull(result);
        verify(departmentsService, times(1)).getOne(id);
    }

    @Test
    void getOne_InvalidId_ThrowsException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(departmentsService.getOne(id)).thenThrow(new RuntimeException("Not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> departmentsController.getOne(id));
        verify(departmentsService, times(1)).getOne(id);
    }

    @Test
    void create_ValidName_ReturnsCreatedDepartment() {
        // Arrange
        String name = "HR";
        DepartmentsDto dto = createDepartmentsDto();
        when(departmentsService.create(name)).thenReturn(dto);

        // Act
        DepartmentsDto result = departmentsController.create(name);

        // Assert
        assertNotNull(result);
        verify(departmentsService, times(1)).create(name);
    }

    @Test
    void patch_ValidInput_ReturnsUpdatedDepartment() throws IOException {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "IT";
        DepartmentsDto dto = createDepartmentsDto();
        when(departmentsService.patch(id, name)).thenReturn(dto);

        // Act
        DepartmentsDto result = departmentsController.patch(id, name);

        // Assert
        assertNotNull(result);
        verify(departmentsService, times(1)).patch(id, name);
    }

    @Test
    void patch_IOException_ThrowsException() throws IOException {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "IT";
        when(departmentsService.patch(id, name)).thenThrow(new IOException("Error"));

        // Act & Assert
        assertThrows(IOException.class, () -> departmentsController.patch(id, name));
        verify(departmentsService, times(1)).patch(id, name);
    }

    @Test
    void delete_ValidId_ReturnsDeletedDepartment() {
        // Arrange
        UUID id = UUID.randomUUID();
        DepartmentsDto dto = createDepartmentsDto();
        when(departmentsService.delete(id)).thenReturn(dto);

        // Act
        DepartmentsDto result = departmentsController.delete(id);

        // Assert
        assertNotNull(result);
        verify(departmentsService, times(1)).delete(id);
    }

    private DepartmentsDto createDepartmentsDto() {
        return new DepartmentsDto("IT");
    }

    private DepartmentsFilter createDepartmentsFilter() {
        return new DepartmentsFilter("IT");
    }
}
