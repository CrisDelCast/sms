package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.PrizeRepository;
import co.edu.icesi.pf.domain.model.records.Prize;
import co.edu.icesi.pf.domain.usecase.prize.*;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters.PrizeRepositoryAdapter;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.PrizeDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.PrizeMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.PrizeDAORepository;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.prize.PrizeController;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.prize.dto.PrizeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PrizeTest {

    @Mock
    private PrizeRepository prizeRepository;

    @Mock
    private PrizeDAORepository repository;

    @Mock
    private PrizeMapper mapper;

    @InjectMocks
    private CreatePrizeUseCase createPrizeUseCase;

    @InjectMocks
    private PrizeRepositoryAdapter adapter;

    @Mock
    private CreatePrizeUseCase mockCreatePrizeUseCase;

    @Mock
    private GetPrizesUseCase mockGetPrizesUseCase;

    @Mock
    private GetPrizeByIdUseCase mockGetPrizeByIdUseCase;

    @Mock
    private UpdatePrizeUseCase mockUpdatePrizeUseCase;

    @Mock
    private DeletePrizeUseCase mockDeletePrizeUseCase;

    @InjectMocks
    private PrizeController prizeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePrizeUseCase_Success() {
        // Arrange
        Prize prize = new Prize("Trophy", "trophy.png", "org-123", "ACTIVE");
        when(prizeRepository.save(any(Prize.class))).thenReturn(prize);

        // Act
        Prize result = createPrizeUseCase.execute(
            prize.name(),
            prize.image(),
            prize.organizationUuid(),
            prize.state()
        );

        // Assert
        assertNotNull(result);
        assertEquals("Trophy", result.name());
        assertEquals("trophy.png", result.image());
        verify(prizeRepository, times(1)).save(any(Prize.class));
    }

    @Test
    void testPrizeRepositoryAdapter_Success() {
        // Arrange
        Prize prize = new Prize("Medal", "medal.png", "org-456", "ACTIVE");
        UUID uuid = UUID.randomUUID();
        
        PrizeDAO prizeDAO = new PrizeDAO(uuid, "Medal", "medal.png", "org-456", "ACTIVE");

        when(mapper.toEntity(any(Prize.class))).thenReturn(prizeDAO);
        when(mapper.toDomain(any(PrizeDAO.class))).thenReturn(prize);
        when(repository.save(any(PrizeDAO.class))).thenReturn(prizeDAO);

        // Act
        Prize result = adapter.save(prize);

        // Assert
        assertNotNull(result);
        assertEquals("Medal", result.name());
        verify(repository, times(1)).save(any(PrizeDAO.class));
    }

    @Test
    void testPrizeController_CreateSuccess() {
        // Arrange
        PrizeDTO dto = new PrizeDTO();
        dto.setName("Cup");
        dto.setImage("cup.png");
        dto.setOrganizationUuid("org-789");
        dto.setState("ACTIVE");

        Prize prize = new Prize("Cup", "cup.png", "org-789", "ACTIVE");
        when(mockCreatePrizeUseCase.execute(anyString(), anyString(), anyString(), anyString())).thenReturn(prize);

        // Act
        ResponseEntity<Prize> response = prizeController.create(dto);

        // Assert
        assertNotNull(response);
        Prize responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cup", responseBody.name());
        verify(mockCreatePrizeUseCase, times(1)).execute(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testGetAllPrizes_Success() {
        // Arrange
        List<Prize> prizes = Arrays.asList(
            new Prize("Prize1", "prize1.png", "org1", "ACTIVE"),
            new Prize("Prize2", "prize2.png", "org2", "ACTIVE")
        );
        when(mockGetPrizesUseCase.execute()).thenReturn(prizes);

        // Act
        ResponseEntity<List<Prize>> response = prizeController.getAll();

        // Assert
        assertNotNull(response);
        List<Prize> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, responseBody.size());
        verify(mockGetPrizesUseCase, times(1)).execute();
    }

    @Test
    void testGetPrizeById_Success() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Prize prize = new Prize(uuid, "Prize1", "prize1.png", "org1", "ACTIVE");
        when(mockGetPrizeByIdUseCase.execute(uuid)).thenReturn(prize);

        // Act
        ResponseEntity<Prize> response = prizeController.getById(uuid);

        // Assert
        assertNotNull(response);
        Prize responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(uuid, responseBody.uuid());
        assertEquals("Prize1", responseBody.name());
        verify(mockGetPrizeByIdUseCase, times(1)).execute(uuid);
    }

    @Test
    void testUpdatePrize_Success() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        PrizeDTO dto = new PrizeDTO();
        dto.setName("Updated Prize");
        dto.setImage("updated.png");
        dto.setOrganizationUuid("org-update");
        dto.setState("ACTIVE");

        Prize updatedPrize = new Prize(uuid, "Updated Prize", "updated.png", "org-update", "ACTIVE");
        when(mockUpdatePrizeUseCase.execute(any(UUID.class), any(PrizeDTO.class))).thenReturn(updatedPrize);

        // Act
        ResponseEntity<Prize> response = prizeController.update(uuid, dto);

        // Assert
        assertNotNull(response);
        Prize responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Prize", responseBody.name());
        verify(mockUpdatePrizeUseCase, times(1)).execute(any(UUID.class), any(PrizeDTO.class));
    }

    @Test
    void testDeletePrize_Success() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Prize deletedPrize = new Prize(uuid, "Deleted Prize", "prize.png", "org1", "D");
        when(mockDeletePrizeUseCase.execute(uuid)).thenReturn(deletedPrize);

        // Act
        ResponseEntity<Prize> response = prizeController.delete(uuid);

        // Assert
        assertNotNull(response);
        Prize responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("D", responseBody.state());
        verify(mockDeletePrizeUseCase, times(1)).execute(uuid);
    }

    @Test
    void testCreatePrize_Failure() {
        // Arrange
        PrizeDTO dto = new PrizeDTO();
        dto.setName("Failed Prize");
        dto.setImage("prize.png");
        dto.setOrganizationUuid("org1");
        dto.setState("ACTIVE");

        when(mockCreatePrizeUseCase.execute(anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Failed to create prize"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> prizeController.create(dto));
        assertEquals("Failed to create prize", exception.getMessage());
        verify(mockCreatePrizeUseCase, times(1)).execute(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testGetPrizeById_NotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(mockGetPrizeByIdUseCase.execute(uuid)).thenThrow(new IllegalArgumentException("Prize not found"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> prizeController.getById(uuid));
        assertEquals("Prize not found", exception.getMessage());
        verify(mockGetPrizeByIdUseCase, times(1)).execute(uuid);
    }

    @Test
    void testUpdatePrize_NotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        PrizeDTO dto = new PrizeDTO();
        dto.setName("Non-existent Prize");
        dto.setImage("prize.png");
        dto.setOrganizationUuid("org1");
        dto.setState("ACTIVE");

        when(mockUpdatePrizeUseCase.execute(any(UUID.class), any(PrizeDTO.class)))
                .thenThrow(new RuntimeException("Prize not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> prizeController.update(uuid, dto));
        assertEquals("Prize not found", exception.getMessage());
        verify(mockUpdatePrizeUseCase, times(1)).execute(any(UUID.class), any(PrizeDTO.class));
    }
}
