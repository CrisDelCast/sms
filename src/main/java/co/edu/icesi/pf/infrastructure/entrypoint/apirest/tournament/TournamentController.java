package co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournament;

import co.edu.icesi.pf.domain.model.records.Tournament;
import co.edu.icesi.pf.domain.usecase.tournament.CreateTournamentUseCase;
import co.edu.icesi.pf.domain.usecase.tournament.DeleteTournamentUseCase;
import co.edu.icesi.pf.domain.usecase.tournament.ListTournaments;
import co.edu.icesi.pf.domain.usecase.tournament.ListTournamentsByState;
import co.edu.icesi.pf.domain.usecase.tournament.UpdateTournamentUseCase;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournament.dto.TournamentDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final CreateTournamentUseCase createTournamentUseCase;
    private final ListTournaments listTournaments;
    private final ListTournamentsByState listTournamentsByState;
    private final UpdateTournamentUseCase updateTournamentUseCase;
    private final DeleteTournamentUseCase deleteTournamentUseCase;

    @PostMapping("/create")
    public ResponseEntity<Tournament> createTournament(@RequestBody TournamentDTO request) {
        Tournament tournament = createTournamentUseCase.execute(request.getName(), request.getOrganizationUuid(), request.getStartingDate(), request.getEndingDate(), request.getState());
        return ResponseEntity.ok(tournament);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable String uuid, @RequestBody TournamentDTO request) {
        // Validar fechas
        if (request.getEndingDate().isBefore(request.getStartingDate())) {
            return ResponseEntity.badRequest().build();
        }

        Tournament tournament = updateTournamentUseCase.execute(
            uuid,
            request.getName(),
            request.getOrganizationUuid(),
            request.getStartingDate(),
            request.getEndingDate(),
            request.getState()
        );

        // Si el torneo no existe, retornamos 404
        if (tournament == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tournament);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteTournament(@PathVariable String uuid) {
        try {
            deleteTournamentUseCase.execute(uuid);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        List<Tournament> tournaments = listTournaments.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Tournament>> getTournamentsByState(@RequestParam String state) {
        List<Tournament> tournaments = listTournamentsByState.getAllTournamentsByState(state);
        return ResponseEntity.ok(tournaments);
    }
}
