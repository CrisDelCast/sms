package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.FootballPool;
import co.edu.icesi.pf.domain.usecase.footballpool.*;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters.FootballPoolRepositoryAdapter;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.FootballPoolDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.FootballPoolMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.FootballPoolDAORepository;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.footballpool.FootballPoolController;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.footballpool.dto.FootballPoolDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FootballPoolTests {

    @Mock
    private FootballPoolRepository footballPoolRepository;

    @InjectMocks
    private CreateFootballPoolUseCase createFootballPoolUseCase;

    @Mock
    private FootballPoolDAORepository repository;

    @InjectMocks
    private FootballPoolRepositoryAdapter adapter;

    @Mock
    private CreateFootballPoolUseCase mockCreateFootballPoolUseCase;

    @InjectMocks
    private FootballPoolController footballPoolController;

    @Mock
    private FootballPoolMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFootballPoolUseCase_Success() {
        FootballPool footballPool = new FootballPool("Pool1", "ACTIVE", "tournament-123", 10, 7, 5, 3, 2, 1, 4, 6);
        when(footballPoolRepository.save(any(FootballPool.class))).thenReturn(footballPool);

        FootballPool result = createFootballPoolUseCase.execute("Pool1", "ACTIVE", "tournament-123", 10, 7, 5, 3, 2, 1, 4, 6);

        assertNotNull(result);
        assertEquals("Pool1", result.name());
        verify(footballPoolRepository, times(1)).save(any(FootballPool.class));
    }

    @Test
    void testCreateFootballPoolUseCase_Failure() {
        when(footballPoolRepository.save(any(FootballPool.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                createFootballPoolUseCase.execute("Pool1", "ACTIVE", "tournament-123", 10, 7, 5, 3, 2, 1, 4, 6)
        );

        assertEquals("Database error", exception.getMessage());
        verify(footballPoolRepository, times(1)).save(any(FootballPool.class));
    }

    @Test
    void testFootballPoolController_Success() {
        FootballPoolDTO dto = new FootballPoolDTO("Pool1", "ACTIVE", "tournament-123", 10, 7, 5, 3, 2, 1, 4, 6);
        FootballPool footballPool = new FootballPool("Pool1", "ACTIVE", "tournament-123", 10, 7, 5, 3, 2, 1, 4, 6);
        when(mockCreateFootballPoolUseCase.execute(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).thenReturn(footballPool);

        ResponseEntity<FootballPool> response = footballPoolController.create(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Pool1", response.getBody().name());
        verify(mockCreateFootballPoolUseCase, times(1)).execute(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testFootballPoolController_Failure() {
        FootballPoolDTO dto = new FootballPoolDTO("Pool1", "ACTIVE", "tournament-123", 10, 7, 5, 3, 2, 1, 4, 6);
        when(mockCreateFootballPoolUseCase.execute(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Service error"));

        Exception exception = assertThrows(RuntimeException.class, () -> footballPoolController.create(dto));

        assertEquals("Service error", exception.getMessage());
        verify(mockCreateFootballPoolUseCase, times(1)).execute(anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }


    @Nested
    class GetFootballPoolsUseCaseTest {

        @Mock
        private FootballPoolRepository footballPoolRepository;

        @InjectMocks
        private GetFooballPoolsUseCase getFooballPoolsUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldGetAllFootballPools() {
            // Given
            UUID uuid1 = UUID.randomUUID();
            UUID uuid2 = UUID.randomUUID();
            String tournamentUuid = UUID.randomUUID().toString();

            FootballPool pool1 = createSampleFootballPool(uuid1, "World Cup 2022", tournamentUuid);
            FootballPool pool2 = createSampleFootballPool(uuid2, "Euro 2024", tournamentUuid);

            List<FootballPool> expected = Arrays.asList(pool1, pool2);

            when(footballPoolRepository.findAll()).thenReturn(expected);

            // When
            List<FootballPool> result = getFooballPoolsUseCase.execute();

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(expected, result);

            verify(footballPoolRepository, times(1)).findAll();
        }

        @Test
        void shouldReturnEmptyListWhenNoFootballPoolsExist() {
            // Given
            when(footballPoolRepository.findAll()).thenReturn(List.of());

            // When
            List<FootballPool> result = getFooballPoolsUseCase.execute();

            // Then
            assertNotNull(result);
            assertEquals(0, result.size());

            verify(footballPoolRepository, times(1)).findAll();
        }

        private FootballPool createSampleFootballPool(UUID uuid, String name, String tournamentUuid) {
            return new FootballPool(
                    uuid,
                    name,
                    "A",
                    tournamentUuid,
                    100,
                    50,
                    25,
                    10,
                    5,
                    3,
                    2,
                    2
            );
        }
    }

    @Nested
    class GetFootballPoolByIdUseCaseTest {

        @Mock
        private FootballPoolRepository footballPoolRepository;

        @InjectMocks
        private GetFootballPoolByIdUseCase getFootballPoolByIdUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldGetFootballPoolById() {
            // Given
            UUID uuid = UUID.randomUUID();
            String name = "Champions League";
            String state = "A";
            String tournamentUuid = UUID.randomUUID().toString();

            FootballPool expected = new FootballPool(
                    uuid,
                    name,
                    state,
                    tournamentUuid,
                    100,
                    50,
                    25,
                    10,
                    5,
                    3,
                    2,
                    2
            );

            when(footballPoolRepository.findByUuid(uuid)).thenReturn(Optional.of(expected));

            // When
            FootballPool result = getFootballPoolByIdUseCase.execute(uuid);

            // Then
            assertNotNull(result);
            assertEquals(expected, result);
            assertEquals(uuid, result.uuid());
            assertEquals(name, result.name());
            assertEquals(state, result.state());
            assertEquals(tournamentUuid, result.tournamentUuid());

            verify(footballPoolRepository, times(1)).findByUuid(uuid);
        }

        @Test
        void shouldThrowExceptionWhenFootballPoolNotFound() {
            // Given
            UUID uuid = UUID.randomUUID();
            when(footballPoolRepository.findByUuid(uuid)).thenReturn(Optional.empty());

            // When & Then
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> getFootballPoolByIdUseCase.execute(uuid)
            );

            assertEquals("FootballPool not found for id: " + uuid, exception.getMessage());
            verify(footballPoolRepository, times(1)).findByUuid(uuid);
        }
    }

    @Nested
    class UpdateFootballPoolUseCaseTest {

        @Mock
        private FootballPoolRepository footballPoolRepository;

        @InjectMocks
        private UpdateFootballPoolUseCase updateFootballPoolUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldUpdateFootballPool() {
            // Given
            UUID uuid = UUID.randomUUID();
            String tournamentUuid = UUID.randomUUID().toString();

            FootballPool existing = new FootballPool(
                    uuid,
                    "Original Name",
                    "A",
                    tournamentUuid,
                    50,
                    25,
                    10,
                    5,
                    3,
                    2,
                    2,
                    1
            );

            FootballPoolDTO updateDto = new FootballPoolDTO(
                    "Updated Name",
                    "B",
                    tournamentUuid,
                    100,
                    50,
                    25,
                    10,
                    5,
                    3,
                    2,
                    2
            );

            FootballPool updated = new FootballPool(
                    uuid,
                    updateDto.getName(),
                    updateDto.getState(),
                    updateDto.getTournamentUuid(),
                    updateDto.getChampionsWinPoints(),
                    updateDto.getSecondPlaceWinPoints(),
                    updateDto.getThirdPlaceWinPoints(),
                    updateDto.getWinnerTeamWinPoints(),
                    updateDto.getTotalYellowCardsWinPoints(),
                    updateDto.getTotalRedCardsWinPoints(),
                    updateDto.getTotalLocalGoalsWinPoints(),
                    updateDto.getTotalVisitingGoalWinPoints()
            );

            when(footballPoolRepository.findByUuid(uuid)).thenReturn(Optional.of(existing));
            when(footballPoolRepository.update(eq(uuid), any(FootballPool.class))).thenReturn(updated);

            // When
            FootballPool result = updateFootballPoolUseCase.execute(uuid, updateDto);

            // Then
            assertNotNull(result);
            assertEquals(uuid, result.uuid());
            assertEquals(updateDto.getName(), result.name());
            assertEquals(updateDto.getState(), result.state());
            assertEquals(updateDto.getTournamentUuid(), result.tournamentUuid());
            assertEquals(updateDto.getChampionsWinPoints(), result.championsWinPoints());
            assertEquals(updateDto.getSecondPlaceWinPoints(), result.secondPlaceWinPoints());
            assertEquals(updateDto.getThirdPlaceWinPoints(), result.thirdPlaceWinPoints());
            assertEquals(updateDto.getWinnerTeamWinPoints(), result.winnerTeamWinPoints());
            assertEquals(updateDto.getTotalYellowCardsWinPoints(), result.totalYellowCardsWinPoints());
            assertEquals(updateDto.getTotalRedCardsWinPoints(), result.totalRedCardsWinPoints());
            assertEquals(updateDto.getTotalLocalGoalsWinPoints(), result.totalLocalGoalsWinPoints());
            assertEquals(updateDto.getTotalVisitingGoalWinPoints(), result.totalVisitingGoalWinPoints());

            verify(footballPoolRepository, times(1)).findByUuid(uuid);
            verify(footballPoolRepository, times(1)).update(eq(uuid), any(FootballPool.class));
        }

        @Test
        void shouldThrowExceptionWhenFootballPoolNotFound() {
            // Given
            UUID uuid = UUID.randomUUID();
            FootballPoolDTO updateDto = new FootballPoolDTO(
                    "Name",
                    "A",
                    UUID.randomUUID().toString(),
                    100,
                    50,
                    25,
                    10,
                    5,
                    3,
                    2,
                    2
            );

            when(footballPoolRepository.findByUuid(uuid)).thenReturn(Optional.empty());

            // When & Then
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> updateFootballPoolUseCase.execute(uuid, updateDto)
            );

            assertEquals("Football Pool not found", exception.getMessage());
            verify(footballPoolRepository, times(1)).findByUuid(uuid);
            verify(footballPoolRepository, never()).update(any(), any());
        }
    }

    @Nested
    class DeleteFootballPoolUseCaseTest {

        @Mock
        private FootballPoolRepository footballPoolRepository;

        @InjectMocks
        private DeleteFootballPoolUseCase deleteFootballPoolUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldSoftDeleteFootballPool() {
            // Given
            UUID uuid = UUID.randomUUID();
            String tournamentUuid = UUID.randomUUID().toString();

            FootballPool original = new FootballPool(
                    uuid,
                    "World Cup 2022",
                    "A",
                    tournamentUuid,
                    100,
                    50,
                    25,
                    10,
                    5,
                    3,
                    2,
                    2
            );

            FootballPool deleted = new FootballPool(
                    uuid,
                    "World Cup 2022",
                    "D",
                    tournamentUuid,
                    100,
                    50,
                    25,
                    10,
                    5,
                    3,
                    2,
                    2
            );

            when(footballPoolRepository.softDelete(uuid)).thenReturn(deleted);

            // When
            FootballPool result = deleteFootballPoolUseCase.execute(uuid);

            // Then
            assertNotNull(result);
            assertEquals(uuid, result.uuid());
            assertEquals(original.name(), result.name());
            assertEquals("D", result.state());
            assertEquals(original.tournamentUuid(), result.tournamentUuid());

            verify(footballPoolRepository, times(1)).softDelete(uuid);
        }
    }
}

