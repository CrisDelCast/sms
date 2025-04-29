package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.records.Tournament;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TournamentDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.TournamentMapperImpl;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.TournamentDAORepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TournamentRepositoryAdapterTest {

    @Mock
    private TournamentDAORepository tournamentDAORepository;

    @Mock
    private TournamentMapperImpl tournamentMapper;

    @InjectMocks
    private TournamentRepositoryAdapter tournamentRepositoryAdapter;

    private static final TournamentDAO TOURNAMENT_1 = new TournamentDAO("827386249", "Mundial de Futbol", "ORG123", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), "ACTIVE");
    private static final TournamentDAO TOURNAMENT_2 = new TournamentDAO("823432942", "Copa America", "ORG456", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 30), "INACTIVE");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll_ShouldReturnAllTournaments() {
        List<TournamentDAO> mockDAOs = Arrays.asList(TOURNAMENT_1, TOURNAMENT_2);

        when(tournamentDAORepository.findAll()).thenReturn(mockDAOs);
        when(tournamentMapper.toDomain(any())).thenAnswer(invocation -> {
            TournamentDAO dao = invocation.getArgument(0);
            return new Tournament(dao.getName(), dao.getOrganizationUuid(), dao.getStartingDate(), dao.getEndingDate(), dao.getState());
        });

        List<Tournament> tournaments = tournamentRepositoryAdapter.findAll();

        assertNotNull(tournaments);
        assertEquals(2, tournaments.size());
        verify(tournamentDAORepository, times(1)).findAll();
    }

    @Test
    void testGetAll_ShouldReturnEmptyList() {
        when(tournamentDAORepository.findAll()).thenReturn(List.of());

        List<Tournament> tournaments = tournamentRepositoryAdapter.findAll();

        assertNotNull(tournaments);
        assertEquals(0, tournaments.size());
        verify(tournamentDAORepository, times(1)).findAll();
    }

    @Test
    void testGetByState_ShouldReturnFilteredTournaments() {
        List<TournamentDAO> mockDAOs = Arrays.asList(TOURNAMENT_1, TOURNAMENT_2);

        when(tournamentDAORepository.findAll()).thenReturn(mockDAOs);
        when(tournamentDAORepository.findByState("ACTIVE")).thenAnswer(invocation ->
                mockDAOs.stream()
                        .filter(t -> t.getState().equals("ACTIVE"))
                        .toList()
        );
        when(tournamentMapper.toDomain(any())).thenAnswer(invocation -> {
            TournamentDAO dao = invocation.getArgument(0);
            return new Tournament(dao.getName(), dao.getOrganizationUuid(), dao.getStartingDate(), dao.getEndingDate(), dao.getState());
        });

        List<Tournament> tournaments = tournamentRepositoryAdapter.findAllByState("ACTIVE");

        assertNotNull(tournaments);
        assertEquals(1, tournaments.size());
        assertEquals(TOURNAMENT_1.getName(), tournaments.getFirst().name());
        verify(tournamentDAORepository, times(1)).findByState("ACTIVE");
    }
}
