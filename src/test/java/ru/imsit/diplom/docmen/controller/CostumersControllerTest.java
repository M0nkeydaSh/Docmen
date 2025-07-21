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
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.filter.CostumersFilter;
import ru.imsit.diplom.docmen.service.CostumersService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CostumersControllerTest {

    @Mock
    private CostumersService costumersService;

    @InjectMocks
    private CostumersController costumersController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    void getAll_ReturnsPagedModel() {
        // Arrange
        CostumersFilter filter = createCostumersFilter();
        Pageable pageable = PageRequest.of(0, 10);
        List<CostumersDto> costumersList = List.of(createCostumerDto(), createCostumerDto());
        Page<CostumersDto> costumersPage = new PageImpl<>(costumersList);

        when(costumersService.getAll(filter, pageable)).thenReturn(costumersPage);

        // Act
        PagedModel<CostumersDto> result = costumersController.getAll(filter, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(costumersService, times(1)).getAll(filter, pageable);
    }

    @Test
    void getOne_ReturnsCostumer() {
        // Arrange
        String username = "testUser";
        CostumersDto costumer = createCostumerDto();

        when(costumersService.getOne(username)).thenReturn(costumer);

        // Act
        CostumersDto result = costumersController.getOne(username);

        // Assert
        assertNotNull(result);
        verify(costumersService, times(1)).getOne(username);
    }

    @Test
    void create_ReturnsCreatedCostumer() throws Exception {
        // Arrange
        String firstname = "John", surName = "Middle", lastName = "Doe", email = "john@example.com",
                phoneNumber = "1234567890", gender = "MALE", typeCostumer = "Manager", username = "johndoe";

        CostumersDto createdCostumer = createCostumerDto();

        when(costumersService.create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username))
                .thenReturn(createdCostumer);

        // Act
        CostumersDto result = costumersController.create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username);

        // Assert
        assertNotNull(result);
        verify(costumersService, times(1)).create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username);
    }

    @Test
    void create_WithOptionalSurName() throws Exception {
        // Arrange
        String firstname = "John", surName = null, lastName = "Doe", email = "john@example.com",
                phoneNumber = "1234567890", gender = "MALE", typeCostumer = "Manager", username = "johndoe";

        CostumersDto createdCostumer = createCostumerDto();

        when(costumersService.create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username))
                .thenReturn(createdCostumer);

        // Act
        CostumersDto result = costumersController.create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username);

        // Assert
        assertNotNull(result);
        verify(costumersService, times(1)).create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username);
    }

    @Test
    void patch_ReturnsUpdatedCostumer() throws IOException {
        // Arrange
        String username = "testUser", firstname = "Jane", surName = "Middle", lastName = "Smith",
                email = "jane@example.com", phoneNumber = "0987654321", gender = "FEMALE", typeCostumer = "Admin";

        CostumersDto updatedCostumer = createCostumerDto();

        when(costumersService.patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer))
                .thenReturn(updatedCostumer);

        // Act
        CostumersDto result = costumersController.patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer);

        // Assert
        assertNotNull(result);
        verify(costumersService, times(1)).patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer);
    }

    @Test
    void patch_ThrowsIOException() throws IOException {
        // Arrange
        String username = "testUser", firstname = "Jane", surName = "Middle", lastName = "Smith",
                email = "jane@example.com", phoneNumber = "0987654321", gender = "FEMALE", typeCostumer = "Admin";
        IOException exception = new IOException("Database error");

        when(costumersService.patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer))
                .thenThrow(exception);

        // Act & Assert
        assertThrows(IOException.class, () -> costumersController.patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer));
        verify(costumersService, times(1)).patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer);
    }

    @Test
    void delete_ReturnsDeletedCostumer() {
        // Arrange
        String username = "testUser";
        CostumersDto deletedCostumer = createCostumerDto();

        when(costumersService.delete(username)).thenReturn(deletedCostumer);

        // Act
        CostumersDto result = costumersController.delete(username);

        // Assert
        assertNotNull(result);
        verify(costumersService, times(1)).delete(username);
    }

    @Test
    void getAll_EmptyPage_ReturnsEmptyModel() {
        // Arrange
        CostumersFilter filter = createCostumersFilter();
        Pageable pageable = PageRequest.of(0, 10);
        Page<CostumersDto> emptyPage = new PageImpl<>(List.of());

        when(costumersService.getAll(filter, pageable)).thenReturn(emptyPage);

        // Act
        PagedModel<CostumersDto> result = costumersController.getAll(filter, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(costumersService, times(1)).getAll(filter, pageable);
    }

    private CostumersDto createCostumerDto() {
        return new CostumersDto(
                "John Doe",
                "john_doe",
                "john@example.com",
                "1234567890",
                "Male",
                "Manager",
                new TypeCostumerDto("Manager",
                        new DepartmentsDto("IT")), "johndoe");
    }
    private CostumersFilter createCostumersFilter() {
        return new CostumersFilter("John Doe",
                "john_doe",
                "john@example.com",
                "M");
    }
}
