package co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournamentPhase;

import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import co.edu.icesi.pf.domain.usecase.tournamentPhase.*;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournamentPhase.dto.TournamentPhaseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/tournament-phase")
public class TournamentPhaseController {

    private final CreateTournamentPhaseUseCase createTournamentPhaseUseCase;
    private final GetTournamentPhaseByIdUseCase getTournamentPhaseByIdUseCase;
    private final GetTournamentPhasesUseCase getTournamentPhasesUseCase;
    private final UpdateTournamentPhaseUseCase updateTournamentPhaseUseCase;
    private final DeleteTournamentPhaseUseCase deleteTournamentPhaseUseCase;

    @PostMapping("/create")
    public ResponseEntity<TournamentPhase> createTournamentPhase(@RequestBody TournamentPhaseDTO request) {
        TournamentPhase tournamentPhase = createTournamentPhaseUseCase.execute(request.getName(), request.getTournamentUuid(), request.getState());
        return ResponseEntity.ok(tournamentPhase);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TournamentPhase>> getTournamentPhases() {
        List<TournamentPhase> tournamentPhases = getTournamentPhasesUseCase.execute();
        return ResponseEntity.ok(tournamentPhases);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<TournamentPhase> getTournamentPhaseById(@PathVariable UUID uuid) {
        TournamentPhase tournamentPhase = getTournamentPhaseByIdUseCase.execute(uuid);
        return ResponseEntity.ok(tournamentPhase);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<TournamentPhase> updateTournamentPhase(@PathVariable UUID uuid, @RequestBody TournamentPhaseDTO request) {
        TournamentPhase updated = updateTournamentPhaseUseCase.execute(uuid, request.getName(), request.getTournamentUuid(), request.getState());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<TournamentPhase> deleteTournamentPhase(@PathVariable UUID uuid) {
        TournamentPhase deleted = deleteTournamentPhaseUseCase.execute(uuid);
        return ResponseEntity.ok(deleted);
    }
}
