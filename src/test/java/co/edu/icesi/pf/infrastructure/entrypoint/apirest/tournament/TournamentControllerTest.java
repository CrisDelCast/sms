package co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournament;

import co.edu.icesi.pf.domain.usecase.tournament.ListTournaments;
import co.edu.icesi.pf.domain.model.records.Tournament;
import co.edu.icesi.pf.domain.usecase.tournament.ListTournamentsByState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TournamentControllerTest {

    @Mock
    private ListTournaments listTournaments;

    @Mock
    private ListTournamentsByState listTournamentsByState;

    @InjectMocks
    private TournamentController tournamentController;

    private static final Tournament TOURNAMENT_1 = new Tournament("Mundial de Futbol", "ORG123", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), "ACTIVE");
    private static final Tournament TOURNAMENT_2 = new Tournament("Copa America", "ORG456", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 30), "INACTIVE");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTournaments_ShouldReturnAllTournaments() {
        List<Tournament> mockTournaments = Arrays.asList(TOURNAMENT_1, TOURNAMENT_2);

        when(listTournaments.getAllTournaments()).thenReturn(mockTournaments);

        ResponseEntity<List<Tournament>> response = tournamentController.getAllTournaments();

        assertNotNull(response);
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals("Mundial de Futbol", response.getBody().getFirst().name());
        verify(listTournaments, times(1)).getAllTournaments();
    }

    @Test
    void testGetAll_ShouldReturnEmptyList() {
        when(listTournaments.getAllTournaments()).thenReturn(List.of());

        ResponseEntity<List<Tournament>> response = tournamentController.getAllTournaments();

        assertNotNull(response);
        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
        verify(listTournaments, times(1)).getAllTournaments();
    }

    @Test
    void testGetTournamentsByState_ShouldReturnFilteredTournaments() {
        List<Tournament> mockTournaments = Arrays.asList(TOURNAMENT_1, TOURNAMENT_2);

        when(listTournamentsByState.getAllTournamentsByState("ACTIVE")).thenAnswer(invocation ->
                mockTournaments.stream()
                        .filter(t -> t.state().equals("ACTIVE"))
                        .toList()
        );

        ResponseEntity<List<Tournament>> response = tournamentController.getTournamentsByState("ACTIVE");

        assertNotNull(response);
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertTrue(response.getBody().stream().allMatch(t -> t.state().equals("ACTIVE")));
        verify(listTournamentsByState, times(1)).getAllTournamentsByState("ACTIVE");
    }
}
