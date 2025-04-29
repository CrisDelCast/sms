package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.TeamRepository;
import co.edu.icesi.pf.domain.model.records.Team;
import co.edu.icesi.pf.domain.usecase.team.*;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters.TeamRepositoryAdapter;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TeamDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.TeamMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.TeamDAORepository;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.team.TeamController;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.team.dto.TeamDTO;
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

class TeamTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamDAORepository repository;

    @Mock
    private TeamMapper mapper;

    @InjectMocks
    private CreateTeamUseCase createTeamUseCase;

    @InjectMocks
    private TeamRepositoryAdapter adapter;

    @Mock
    private CreateTeamUseCase mockCreateTeamUseCase;

    @Mock
    private GetTeamsUseCase mockGetTeamsUseCase;

    @Mock
    private GetTeamByIdUseCase mockGetTeamByIdUseCase;

    @Mock
    private UpdateTeamUseCase mockUpdateTeamUseCase;

    @Mock
    private DeleteTeamUseCase mockDeleteTeamUseCase;

    @InjectMocks
    private TeamController teamController;

    @Test
    void testCreateTeamUseCase_Success() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        Team team = new Team("Real Madrid", "RMA", "spain_flag.png", 0);
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        // Act
        Team result = createTeamUseCase.execute(
            team.name(),
            team.nameAbbreviation(),
            team.teamFlag(),
            team.points()
        );

        // Assert
        assertNotNull(result);
        assertEquals("Real Madrid", result.name());
        assertEquals("RMA", result.nameAbbreviation());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void testTeamRepositoryAdapter_Success() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        Team team = new Team("Barcelona", "BAR", "spain_flag.png", 0);
        UUID uuid = UUID.randomUUID();
        
        TeamDAO teamDAO = new TeamDAO();
        teamDAO.setUuid(uuid);
        teamDAO.setName("Barcelona");
        teamDAO.setNameAbbreviation("BAR");
        teamDAO.setTeamFlag("spain_flag.png");
        teamDAO.setPoints(0);

        when(mapper.toEntity(any(Team.class))).thenReturn(teamDAO);
        when(mapper.toDomain(any(TeamDAO.class))).thenReturn(team);
        when(repository.save(any(TeamDAO.class))).thenReturn(teamDAO);

        // Act
        Team result = adapter.save(team);

        // Assert
        assertNotNull(result);
        assertEquals("Barcelona", result.name());
        verify(repository, times(1)).save(any(TeamDAO.class));
    }

    @Test
    void testTeamController_CreateSuccess() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        TeamDTO dto = new TeamDTO();
        dto.setName("Bayern Munich");
        dto.setNameAbbreviation("BAY");
        dto.setTeamFlag("germany_flag.png");
        dto.setPoints(0);

        Team team = new Team("Bayern Munich", "BAY", "germany_flag.png", 0);
        when(mockCreateTeamUseCase.execute(anyString(), anyString(), anyString(), anyInt())).thenReturn(team);

        // Act
        ResponseEntity<Team> response = teamController.create(dto);

        // Assert
        assertNotNull(response);
        Team responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Bayern Munich", responseBody.name());
        verify(mockCreateTeamUseCase, times(1)).execute(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void testGetAllTeams_Success() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        List<Team> teams = Arrays.asList(
            new Team("Team1", "TM1", "flag1.png", 0),
            new Team("Team2", "TM2", "flag2.png", 0)
        );
        when(mockGetTeamsUseCase.execute()).thenReturn(teams);

        // Act
        ResponseEntity<List<Team>> response = teamController.getAll();

        // Assert
        assertNotNull(response);
        List<Team> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, responseBody.size());
        verify(mockGetTeamsUseCase, times(1)).execute();
    }

    @Test
    void testGetTeamById_Success() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        UUID uuid = UUID.randomUUID();
        Team team = new Team(uuid, "Team1", "TM1", "flag1.png", 0);
        when(mockGetTeamByIdUseCase.execute(uuid)).thenReturn(team);

        // Act
        ResponseEntity<Team> response = teamController.getById(uuid);

        // Assert
        assertNotNull(response);
        Team responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(uuid, responseBody.uuid());
        assertEquals("Team1", responseBody.name());
        verify(mockGetTeamByIdUseCase, times(1)).execute(uuid);
    }

    @Test
    void testUpdateTeam_Success() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        UUID uuid = UUID.randomUUID();
        TeamDTO dto = new TeamDTO();
        dto.setName("Updated Team");
        dto.setNameAbbreviation("UTM");
        dto.setTeamFlag("flag_updated.png");
        dto.setPoints(10);

        Team updatedTeam = new Team(uuid, "Updated Team", "UTM", "flag_updated.png", 10);
        when(mockUpdateTeamUseCase.execute(any(UUID.class), any(TeamDTO.class))).thenReturn(updatedTeam);

        // Act
        ResponseEntity<Team> response = teamController.update(uuid, dto);

        // Assert
        assertNotNull(response);
        Team responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Team", responseBody.name());
        assertEquals(10, responseBody.points());
        verify(mockUpdateTeamUseCase, times(1)).execute(any(UUID.class), any(TeamDTO.class));
    }

    @Test
    void testDeleteTeam_Success() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        UUID uuid = UUID.randomUUID();
        Team deletedTeam = new Team(uuid, "Deleted Team", "DTM", "flag.png", 0);
        when(mockDeleteTeamUseCase.execute(uuid)).thenReturn(deletedTeam);

        // Act
        ResponseEntity<Team> response = teamController.delete(uuid);

        // Assert
        assertNotNull(response);
        Team responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(uuid, responseBody.uuid());
        verify(mockDeleteTeamUseCase, times(1)).execute(uuid);
    }

    @Test
    void testCreateTeam_Failure() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        TeamDTO dto = new TeamDTO();
        dto.setName("Failed Team");
        dto.setNameAbbreviation("FTM");
        dto.setTeamFlag("flag.png");
        dto.setPoints(0);

        when(mockCreateTeamUseCase.execute(anyString(), anyString(), anyString(), anyInt()))
                .thenThrow(new RuntimeException("Failed to create team"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> teamController.create(dto));
        assertEquals("Failed to create team", exception.getMessage());
        verify(mockCreateTeamUseCase, times(1)).execute(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void testGetTeamById_NotFound() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        UUID uuid = UUID.randomUUID();
        when(mockGetTeamByIdUseCase.execute(uuid)).thenThrow(new RuntimeException("Team not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> teamController.getById(uuid));
        assertEquals("Team not found", exception.getMessage());
        verify(mockGetTeamByIdUseCase, times(1)).execute(uuid);
    }

    @Test
    void testUpdateTeam_NotFound() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        UUID uuid = UUID.randomUUID();
        TeamDTO dto = new TeamDTO();
        dto.setName("Non-existent Team");
        dto.setNameAbbreviation("NET");
        dto.setTeamFlag("flag.png");
        dto.setPoints(0);

        when(mockUpdateTeamUseCase.execute(any(UUID.class), any(TeamDTO.class)))
                .thenThrow(new RuntimeException("Team not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> teamController.update(uuid, dto));
        assertEquals("Team not found", exception.getMessage());
        verify(mockUpdateTeamUseCase, times(1)).execute(any(UUID.class), any(TeamDTO.class));
    }
}
