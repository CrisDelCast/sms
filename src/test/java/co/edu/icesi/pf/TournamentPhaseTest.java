package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentPhaseRepository;
import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import co.edu.icesi.pf.domain.usecase.tournamentPhase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TournamentPhaseTest {

    @Nested
    class CreateTournamentPhaseUseCaseTest {

        @Mock
        private TournamentPhaseRepository tournamentPhaseRepository;

        @InjectMocks
        private CreateTournamentPhaseUseCase createTournamentPhaseUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldCreateTournamentPhase() {
            // Given
            String name = "Group Stage";
            String tournamentUuid = UUID.randomUUID().toString();
            String state = "A";

            UUID generatedUuid = UUID.randomUUID();
            TournamentPhase expected = new TournamentPhase(generatedUuid, name, tournamentUuid, state);

            when(tournamentPhaseRepository.save(any(TournamentPhase.class))).thenReturn(expected);

            // When
            TournamentPhase result = createTournamentPhaseUseCase.execute(name, tournamentUuid, state);

            // Then
            assertNotNull(result);
            assertEquals(expected, result);
            assertEquals(generatedUuid, result.uuid());
            assertEquals(name, result.name());
            assertEquals(tournamentUuid, result.tournamentUuid());
            assertEquals(state, result.state());

            verify(tournamentPhaseRepository, times(1)).save(any(TournamentPhase.class));
        }
    }

    @Nested
    class GetTournamentPhaseByIdUseCaseTest {

        @Mock
        private TournamentPhaseRepository tournamentPhaseRepository;

        @InjectMocks
        private GetTournamentPhaseByIdUseCase getTournamentPhaseByIdUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldGetTournamentPhaseById() {
            // Given
            UUID uuid = UUID.randomUUID();
            String name = "Knockout Stage";
            String tournamentUuid = UUID.randomUUID().toString();
            String state = "A";

            TournamentPhase tournamentPhase = new TournamentPhase(uuid, name, tournamentUuid, state);

            when(tournamentPhaseRepository.findByUuid(uuid)).thenReturn(Optional.of(tournamentPhase));

            // When
            TournamentPhase result = getTournamentPhaseByIdUseCase.execute(uuid);

            // Then
            assertNotNull(result);
            assertEquals(uuid, result.uuid());
            assertEquals(name, result.name());
            assertEquals(tournamentUuid, result.tournamentUuid());
            assertEquals(state, result.state());

            verify(tournamentPhaseRepository, times(1)).findByUuid(uuid);
        }

        @Test
        void shouldThrowExceptionWhenTournamentPhaseNotFound() {
            // Given
            UUID uuid = UUID.randomUUID();
            when(tournamentPhaseRepository.findByUuid(uuid)).thenReturn(Optional.empty());

            // When & Then
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> getTournamentPhaseByIdUseCase.execute(uuid)
            );

            assertEquals("Tournament phase not found for id: " + uuid, exception.getMessage());
            verify(tournamentPhaseRepository, times(1)).findByUuid(uuid);
        }
    }

    @Nested
    class GetTournamentPhasesUseCaseTest {

        @Mock
        private TournamentPhaseRepository tournamentPhaseRepository;

        @InjectMocks
        private GetTournamentPhasesUseCase getTournamentPhasesUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldGetAllTournamentPhases() {
            // Given
            String tournamentUuid = UUID.randomUUID().toString();

            TournamentPhase phase1 = new TournamentPhase(UUID.randomUUID(), "Group Stage", tournamentUuid, "A");
            TournamentPhase phase2 = new TournamentPhase(UUID.randomUUID(), "Knockout Stage", tournamentUuid, "A");
            List<TournamentPhase> expected = Arrays.asList(phase1, phase2);

            when(tournamentPhaseRepository.findAll()).thenReturn(expected);

            // When
            List<TournamentPhase> result = getTournamentPhasesUseCase.execute();

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(expected, result);

            verify(tournamentPhaseRepository, times(1)).findAll();
        }

        @Test
        void shouldReturnEmptyListWhenNoTournamentPhasesExist() {
            // Given
            when(tournamentPhaseRepository.findAll()).thenReturn(List.of());

            // When
            List<TournamentPhase> result = getTournamentPhasesUseCase.execute();

            // Then
            assertNotNull(result);
            assertEquals(0, result.size());

            verify(tournamentPhaseRepository, times(1)).findAll();
        }
    }

    @Nested
    class UpdateTournamentPhaseUseCaseTest {

        @Mock
        private TournamentPhaseRepository tournamentPhaseRepository;

        @InjectMocks
        private UpdateTournamentPhaseUseCase updateTournamentPhaseUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldUpdateTournamentPhase() {
            // Given
            UUID uuid = UUID.randomUUID();
            String originalName = "Original Name";
            String originalTournamentUuid = UUID.randomUUID().toString();
            String originalState = "A";

            String updatedName = "Updated Name";
            String updatedTournamentUuid = UUID.randomUUID().toString();
            String updatedState = "B";

            TournamentPhase existingPhase = new TournamentPhase(uuid, originalName, originalTournamentUuid, originalState);
            TournamentPhase updatedPhase = new TournamentPhase(uuid, updatedName, updatedTournamentUuid, updatedState);

            when(tournamentPhaseRepository.findByUuid(uuid)).thenReturn(Optional.of(existingPhase));
            when(tournamentPhaseRepository.update(eq(uuid), any(TournamentPhase.class))).thenReturn(updatedPhase);

            // When
            TournamentPhase result = updateTournamentPhaseUseCase.execute(uuid, updatedName, updatedTournamentUuid, updatedState);

            // Then
            assertNotNull(result);
            assertEquals(uuid, result.uuid());
            assertEquals(updatedName, result.name());
            assertEquals(updatedTournamentUuid, result.tournamentUuid());
            assertEquals(updatedState, result.state());

            verify(tournamentPhaseRepository, times(1)).findByUuid(uuid);
            verify(tournamentPhaseRepository, times(1)).update(eq(uuid), any(TournamentPhase.class));
        }

        @Test
        void shouldThrowExceptionWhenTournamentPhaseNotFound() {
            // Given
            UUID uuid = UUID.randomUUID();
            when(tournamentPhaseRepository.findByUuid(uuid)).thenReturn(Optional.empty());

            // When & Then
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> updateTournamentPhaseUseCase.execute(uuid, "name", "tournamentUuid", "state")
            );

            assertEquals("Tournament phase not found", exception.getMessage());
            verify(tournamentPhaseRepository, times(1)).findByUuid(uuid);
            verify(tournamentPhaseRepository, never()).update(any(), any());
        }
    }

    @Nested
    class DeleteTournamentPhaseUseCaseTest {

        @Mock
        private TournamentPhaseRepository tournamentPhaseRepository;

        @InjectMocks
        private DeleteTournamentPhaseUseCase deleteTournamentPhaseUseCase;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void shouldSoftDeleteTournamentPhase() {
            // Given
            UUID uuid = UUID.randomUUID();
            String name = "Final Stage";
            String tournamentUuid = UUID.randomUUID().toString();
            String originalState = "A";
            String deletedState = "D";

            TournamentPhase original = new TournamentPhase(uuid, name, tournamentUuid, originalState);
            TournamentPhase deleted = new TournamentPhase(uuid, name, tournamentUuid, deletedState);

            when(tournamentPhaseRepository.softDelete(uuid)).thenReturn(deleted);

            // When
            TournamentPhase result = deleteTournamentPhaseUseCase.execute(uuid);

            // Then
            assertNotNull(result);
            assertEquals(uuid, result.uuid());
            assertEquals(name, result.name());
            assertEquals(tournamentUuid, result.tournamentUuid());
            assertEquals(deletedState, result.state());

            verify(tournamentPhaseRepository, times(1)).softDelete(uuid);
        }
    }
}