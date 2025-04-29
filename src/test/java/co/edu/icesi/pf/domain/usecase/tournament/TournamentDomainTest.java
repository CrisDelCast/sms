package co.edu.icesi.pf.domain.usecase.tournament;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import co.edu.icesi.pf.domain.model.records.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TournamentDomainTest {
    private ListTournaments listTournaments;
    private ListTournamentsByState listTournamentsByState;
    private TournamentRepository tournamentRepository;

    private static final Tournament TOURNAMENT_1 = new Tournament("Mundial de Futbol", "ORG123", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), "ACTIVE");
    private static final Tournament TOURNAMENT_2 = new Tournament("Copa America", "ORG456", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 30), "INACTIVE");

    @BeforeEach
    void setup() {
        tournamentRepository = Mockito.mock(TournamentRepository.class);
        listTournaments = new ListTournaments(tournamentRepository);
        listTournamentsByState = new ListTournamentsByState(tournamentRepository);
    }

    @Test
    void testGetAll_ShouldReturnAllTournaments() {
        List<Tournament> mockTournaments = Arrays.asList(TOURNAMENT_1, TOURNAMENT_2);
        when(tournamentRepository.findAll()).thenReturn(mockTournaments);

        List<Tournament> result = listTournaments.getAllTournaments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TOURNAMENT_1.name(), result.get(0).name());
        assertEquals(TOURNAMENT_2.name(), result.get(1).name());
        verify(tournamentRepository, times(1)).findAll();
    }

    @Test
    void testGetAll_ShouldReturnEmptyList() {
        List<Tournament> mockTournaments = List.of();
        when(tournamentRepository.findAll()).thenReturn(mockTournaments);

        List<Tournament> result = listTournaments.getAllTournaments();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(tournamentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllByState_ShouldReturnOnlyActiveTournaments() {
        List<Tournament> mockTournaments = List.of(TOURNAMENT_1, TOURNAMENT_2);
        when(tournamentRepository.findAllByState("ACTIVE")).thenAnswer(invocation ->
                mockTournaments.stream()
                        .filter(t -> t.state().equals("ACTIVE"))
                        .toList()
        );

        List<Tournament> result = listTournamentsByState.getAllTournamentsByState("ACTIVE");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(t -> t.state().equals("ACTIVE")));
        verify(tournamentRepository, times(1)).findAllByState("ACTIVE");
    }
}
