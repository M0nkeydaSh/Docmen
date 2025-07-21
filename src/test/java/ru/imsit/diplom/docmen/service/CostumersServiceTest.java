package ru.imsit.diplom.docmen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.entity.Costumers;
import ru.imsit.diplom.docmen.entity.TypeCostumer;
import ru.imsit.diplom.docmen.entity.User;
import ru.imsit.diplom.docmen.filter.CostumersFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.CostumersMapper;
import ru.imsit.diplom.docmen.repository.CostumersRepository;
import ru.imsit.diplom.docmen.repository.TypeCostumerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CostumersServiceTest {

    @Mock
    private UserInfoHelper userInfoHelper;

    @Mock
    private TypeCostumerRepository typeCostumerRepository;

    @Mock
    private CostumersMapper costumersMapper;

    @Mock
    private CostumersRepository costumersRepository;

    @InjectMocks
    private CostumersService costumersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll_ReturnsPageOfDto() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        Specification<Costumers> spec = mock(Specification.class);

        // ✅ Создаём мок для фильтра вместо реального экземпляра
        CostumersFilter filter = mock(CostumersFilter.class);
        when(filter.toSpecification()).thenReturn(spec);  // Теперь это корректно

        List<Costumers> entities = List.of(mock(Costumers.class));
        when(costumersRepository.findAll(spec, pageable)).thenReturn(new PageImpl<>(entities));

        // Act
        Page<CostumersDto> result = costumersService.getAll(filter, pageable);

        // Assert
        assertNotNull(result);
        verify(costumersMapper, times(entities.size())).toCostumersDto(any());
    }


    @Test
    void testGetOne_ClientNotFound_ThrowsException() {
        // Arrange
        String username = "testUser";
        when(costumersRepository.findByUser_Username(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> costumersService.getOne(username));
    }

    @Test
    void testCreate_ValidData_CreatesClient() throws Exception {
        // Arrange
        String username = "testUser";
        User user = mock(User.class);
        TypeCostumer type = mock(TypeCostumer.class);
        Costumers client = mock(Costumers.class);
        CostumersDto dto = mock(CostumersDto.class);

        when(userInfoHelper.getUserByUsername(username)).thenReturn(user);
        when(typeCostumerRepository.findByName("Employee")).thenReturn(Optional.of(type));
        when(costumersRepository.save(any())).thenReturn(client);
        when(costumersMapper.toCostumersDto(client)).thenReturn(dto);  // ✅ Настройка мока

        // Act
        CostumersDto result = costumersService.create(
                "John", "Doe", "Smith", "john@example.com",
                "1234567890", "MALE", "Employee", username
        );

        // Assert
        assertNotNull(result);
        verify(costumersMapper, times(1)).toCostumersDto(client);
    }


    @Test
    void testPatch_ClientNotFound_ThrowsException() {
        // Arrange
        String username = "nonExistent";
        when(costumersRepository.findByUser_Username(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> costumersService.patch(username, "New", "Name", "Last", "new@example.com", "0987654321", "FEMALE", "Manager"));
    }

    @Test
    void testDelete_ClientExists_DeletesAndReturnsDto() {
        // Arrange
        String username = "existingUser";
        Costumers client = mock(Costumers.class);
        when(costumersRepository.findByUser_Username(username)).thenReturn(Optional.of(client));
        when(costumersMapper.toCostumersDto(client)).thenReturn(mock(CostumersDto.class));

        // Act
        CostumersDto result = costumersService.delete(username);

        // Assert
        assertNotNull(result);
        verify(costumersRepository, times(1)).delete(client);
    }

    private CostumersFilter createCostumersFilter() {
        return new CostumersFilter("John Doe",
                "john_doe",
                "john@example.com",
                "M");
    }
}
