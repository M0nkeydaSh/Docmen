package ru.imsit.diplom.docmen.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.entity.Departments;
import ru.imsit.diplom.docmen.filter.DepartmentsFilter;
import ru.imsit.diplom.docmen.mapper.DepartmentsMapper;
import ru.imsit.diplom.docmen.repository.DepartmentsRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentsServiceTest {

    @Mock
    private DepartmentsRepository departmentsRepository;

    @Mock
    private DepartmentsMapper departmentsMapper;

    @InjectMocks
    private DepartmentsService departmentsService;

    @Test
    void getAll_WithFilterAndPageable_ReturnsMappedPage() {
        // Arrange
        DepartmentsFilter filter = mock(DepartmentsFilter.class);
        Pageable pageable = mock(Pageable.class);
        Specification<Departments> spec = mock(Specification.class);
        when(filter.toSpecification()).thenReturn(spec);

        Departments department1 = createDepartments(UUID.randomUUID(),"HR");
        Departments department2 = createDepartments(UUID.randomUUID(),"IT");
        Page<Departments> page = new PageImpl<>(List.of(department1, department2));
        when(departmentsRepository.findAll(spec, pageable)).thenReturn(page);

        DepartmentsDto dto1 = createDepartmentsDto();
        DepartmentsDto dto2 = createDepartmentsDto();
        when(departmentsMapper.toDepartmentsDto(department1)).thenReturn(dto1);
        when(departmentsMapper.toDepartmentsDto(department2)).thenReturn(dto2);

        // Act
        Page<DepartmentsDto> result = departmentsService.getAll(filter, pageable);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals(dto1, result.getContent().get(0));
        assertEquals(dto2, result.getContent().get(1));
        verify(departmentsRepository).findAll(spec, pageable);
    }

    @Test
    void getOne_EntityNotFound_ThrowsResponseStatusException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(departmentsRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> departmentsService.getOne(id));
    }

    @Test
    void create_ValidName_CreatesDepartment() {
        // Arrange
        String name = "Finance";
        Departments department = createDepartments(UUID.randomUUID(),name);
        DepartmentsDto dto = createDepartmentsDto();

        when(departmentsRepository.save(any(Departments.class))).thenReturn(department);
        when(departmentsMapper.toDepartmentsDto(department)).thenReturn(dto);

        // Act
        DepartmentsDto result = departmentsService.create(name);

        // Assert
        assertNotNull(result);
        assertEquals(dto, result);
        verify(departmentsRepository).save(argThat(d -> d.getName().equals(name)));
    }

    @Test
    void patch_DepartmentNotFound_ThrowsRuntimeException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(departmentsRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> departmentsService.patch(id, "New Name"));
    }

    @Test
    void patch_SuccessfulUpdate_ReturnsUpdatedDto() throws IOException {
        // Arrange
        UUID id = UUID.randomUUID();
        Departments existing = createDepartments(id,"Old Name");
        existing.setChangeDate(LocalDateTime.now());
        when(departmentsRepository.findById(id)).thenReturn(Optional.of(existing));

        Departments updated = createDepartments(id,"New Name");
        existing.setChangeDate(LocalDateTime.now().plusDays(1));
        when(departmentsRepository.save(existing)).thenReturn(updated);
        when(departmentsMapper.toDepartmentsDto(updated)).thenReturn(createDepartmentsDto());

        // Act
        DepartmentsDto result = departmentsService.patch(id, "New Name");

        // Assert
        assertNotNull(result);
        assertEquals("New Name", existing.getName());
        assertNotNull(existing.getChangeDate());
        verify(departmentsRepository).save(existing);
    }

    @Test
    void delete_DepartmentNotFound_ThrowsRuntimeException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(departmentsRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> departmentsService.delete(id));
    }

    @Test
    void delete_SuccessfulDelete_ReturnsDeletedDto() {
        // Arrange
        UUID id = UUID.randomUUID();
        Departments department = createDepartments( id,"IT");
        when(departmentsRepository.findById(id)).thenReturn(Optional.of(department));
        DepartmentsDto dto = createDepartmentsDto();
        when(departmentsMapper.toDepartmentsDto(department)).thenReturn(dto);

        // Act
        DepartmentsDto result = departmentsService.delete(id);

        // Assert
        assertEquals(dto, result);
        verify(departmentsRepository).delete(department);
    }

    private DepartmentsDto createDepartmentsDto() {
        return new DepartmentsDto("IT");
    }

    private DepartmentsFilter createDepartmentsFilter() {
        return new DepartmentsFilter("IT");
    }

    private Departments createDepartments(UUID id, String name) {
        Departments department = Departments.builder().name(name).build();
        department.setId(id);
        return department;
    }
}
