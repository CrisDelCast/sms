package co.edu.icesi.pf.infrastructure.entrypoint.apirest.team;

import co.edu.icesi.pf.domain.model.records.Team;
import co.edu.icesi.pf.domain.usecase.team.*;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.team.dto.TeamDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final CreateTeamUseCase createUseCase;
    private final GetTeamsUseCase getTeamsUseCase;
    private final GetTeamByIdUseCase getTeamByIdUseCase;
    private final UpdateTeamUseCase updateTeamUseCase;
    private final DeleteTeamUseCase deleteTeamUseCase;

    @PostMapping("/create")
    public ResponseEntity<Team> create(@RequestBody TeamDTO request) {
        Team team = createUseCase.execute(
                request.getName(),
                request.getNameAbbreviation(),
                request.getTeamFlag(),
                request.getPoints()
        );

        return ResponseEntity.ok(team);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Team>> getAll() {
        List<Team> teams = getTeamsUseCase.execute();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<Team> getById(@PathVariable UUID uuid) {
        Team team = getTeamByIdUseCase.execute(uuid);
        return ResponseEntity.ok(team);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Team> update(@PathVariable UUID uuid, @RequestBody TeamDTO request) {
        Team updated = updateTeamUseCase.execute(uuid, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Team> delete(@PathVariable UUID uuid) {
        Team deleted = deleteTeamUseCase.execute(uuid);
        return ResponseEntity.ok(deleted);
    }
}