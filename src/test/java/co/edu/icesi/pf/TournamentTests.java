package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import co.edu.icesi.pf.domain.model.records.Tournament;
import co.edu.icesi.pf.domain.usecase.tournament.CreateTournamentUseCase;
import co.edu.icesi.pf.domain.usecase.tournament.DeleteTournamentUseCase;
import co.edu.icesi.pf.domain.usecase.tournament.UpdateTournamentUseCase;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters.TournamentRepositoryAdapter;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TournamentDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.TournamentMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.TournamentMapperImpl;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.TournamentDAORepository;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournament.TournamentController;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournament.dto.TournamentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TournamentTests {

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private CreateTournamentUseCase createTournamentUseCase;

    @Mock
    private TournamentDAORepository repository;

    @InjectMocks
    private TournamentRepositoryAdapter adapter;

    @Mock
    private CreateTournamentUseCase mockCreateTournamentUseCase;

    @Mock
    private UpdateTournamentUseCase mockUpdateTournamentUseCase;

    @Mock
    private DeleteTournamentUseCase mockDeleteTournamentUseCase;

    @InjectMocks
    private TournamentController tournamentController;

    @Mock
    private TournamentMapperImpl mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTournamentUseCase_Success() {
        Tournament tournament = new Tournament("Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);

        Tournament result = createTournamentUseCase.execute("Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");

        assertEquals("Torneo1", result.name());
        assertEquals("org-123", result.organizationUuid());
        verify(tournamentRepository, times(1)).save(any(Tournament.class));
    }

    @Test
    void testTournamentRepositoryAdapter_Success() {
        Tournament tournament = new Tournament("Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        TournamentDAO tournamentDAO = new TournamentDAO("1234567", "Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");

        when(mapper.toEntity(any(Tournament.class))).thenReturn(tournamentDAO);
        when(mapper.toDomain(any(TournamentDAO.class))).thenReturn(tournament);
        when(repository.save(any(TournamentDAO.class))).thenReturn(tournamentDAO);

        Tournament result = adapter.save(tournament);

        assertEquals("Torneo1", result.name());
        assertEquals("org-123", result.organizationUuid());
        verify(repository, times(1)).save(any(TournamentDAO.class));
    }

    @Test
    void testTournamentController_Success() {
        TournamentDTO dto = new TournamentDTO("Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        Tournament tournament = new Tournament("Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        when(mockCreateTournamentUseCase.execute(anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(tournament);

        ResponseEntity<Tournament> response = tournamentController.createTournament(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Torneo1", response.getBody().name());
        verify(mockCreateTournamentUseCase, times(1)).execute(anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString());
    }

    @Test
    void testUpdateTournamentUseCase_Success() {
        String tournamentUuid = UUID.randomUUID().toString();
        Tournament tournament = new Tournament(tournamentUuid, "Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        when(tournamentRepository.update(any(Tournament.class))).thenReturn(tournament);

        Tournament result = tournamentRepository.update(tournament);

        assertEquals("Torneo1", result.name());
        assertEquals("org-123", result.organizationUuid());
        verify(tournamentRepository, times(1)).update(any(Tournament.class));
    }

    @Test
    void testUpdateTournamentRepositoryAdapter_Success() {
        String tournamentUuid = UUID.randomUUID().toString();
        Tournament tournament = new Tournament(tournamentUuid, "Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        TournamentDAO tournamentDAO = new TournamentDAO("12345678", "Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        tournamentDAO.setUuid(tournamentUuid);

        when(mapper.toEntity(any(Tournament.class))).thenReturn(tournamentDAO);
        when(mapper.toDomain(any(TournamentDAO.class))).thenReturn(tournament);
        when(repository.findById(anyString())).thenReturn(java.util.Optional.of(tournamentDAO));
        when(repository.save(any(TournamentDAO.class))).thenReturn(tournamentDAO);

        Tournament result = adapter.update(tournament);

        assertEquals("Torneo1", result.name());
        assertEquals("org-123", result.organizationUuid());
        verify(repository, times(1)).findById(anyString());
        verify(repository, times(1)).save(any(TournamentDAO.class));
    }

    @Test
    void testUpdateTournamentController_Success() {
        String tournamentUuid = UUID.randomUUID().toString();
        TournamentDTO dto = new TournamentDTO("Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        Tournament tournament = new Tournament(tournamentUuid, "Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        when(mockUpdateTournamentUseCase.execute(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(tournament);

        ResponseEntity<Tournament> response = tournamentController.updateTournament(tournamentUuid, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Torneo1", response.getBody().name());
        verify(mockUpdateTournamentUseCase, times(1)).execute(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString());
    }

    @Test
    void testUpdateTournamentController_NotFound() {
        String tournamentUuid = UUID.randomUUID().toString();
        TournamentDTO dto = new TournamentDTO("Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        when(mockUpdateTournamentUseCase.execute(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(null);

        ResponseEntity<Tournament> response = tournamentController.updateTournament(tournamentUuid, dto);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
        verify(mockUpdateTournamentUseCase, times(1)).execute(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString());
    }

    @Test
    void testUpdateTournamentController_InvalidDates() {
        String tournamentUuid = UUID.randomUUID().toString();
        TournamentDTO dto = new TournamentDTO("Torneo1", "org-123", LocalDate.now(), LocalDate.now().minusDays(1), "ACTIVE");

        ResponseEntity<Tournament> response = tournamentController.updateTournament(tournamentUuid, dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
        verify(mockUpdateTournamentUseCase, times(0)).execute(anyString(), anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString());
    }

    @Test
    void testDeleteTournamentUseCase_Success() {
        String tournamentUuid = UUID.randomUUID().toString();

        tournamentRepository.delete(tournamentUuid);

        verify(tournamentRepository, times(1)).delete(tournamentUuid);
    }

    @Test
    void testDeleteTournamentRepositoryAdapter_Success() {
        String tournamentUuid = UUID.randomUUID().toString();
        TournamentDAO tournamentDAO = new TournamentDAO("12345678", "Torneo1", "org-123", LocalDate.now(), LocalDate.now().plusDays(5), "ACTIVE");
        tournamentDAO.setUuid(tournamentUuid);

        when(repository.findById(anyString())).thenReturn(java.util.Optional.of(tournamentDAO));
        when(repository.save(any(TournamentDAO.class))).thenReturn(tournamentDAO);

        adapter.delete(tournamentUuid);

        verify(repository, times(1)).findById(tournamentUuid);
        verify(repository, times(1)).save(any(TournamentDAO.class));
        assertEquals("D", tournamentDAO.getState());
    }

    @Test
    void testDeleteTournamentController_Success() {
        String tournamentUuid = UUID.randomUUID().toString();
        doNothing().when(mockDeleteTournamentUseCase).execute(tournamentUuid);

        ResponseEntity<Void> response = tournamentController.deleteTournament(tournamentUuid);

        assertEquals(204, response.getStatusCodeValue());
        verify(mockDeleteTournamentUseCase, times(1)).execute(tournamentUuid);
    }

    @Test
    void testDeleteTournamentController_NotFound() {
        String tournamentUuid = UUID.randomUUID().toString();
        doThrow(new RuntimeException("Tournament not found")).when(mockDeleteTournamentUseCase).execute(tournamentUuid);

        ResponseEntity<Void> response = tournamentController.deleteTournament(tournamentUuid);

        assertEquals(404, response.getStatusCodeValue());
        verify(mockDeleteTournamentUseCase, times(1)).execute(tournamentUuid);
    }
}