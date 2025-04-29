package co.edu.icesi.pf.infrastructure.entrypoint.apirest.footballpool;

import co.edu.icesi.pf.domain.model.records.FootballPool;
import co.edu.icesi.pf.domain.usecase.footballpool.*;

import co.edu.icesi.pf.infrastructure.entrypoint.apirest.footballpool.dto.FootballPoolDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/football-pools")
public class FootballPoolController {

    private final CreateFootballPoolUseCase createUseCase;
    private final GetFooballPoolsUseCase getFooballPoolsUseCase;
    private final GetFootballPoolByIdUseCase getFootballPoolByIdUseCase;
    private final UpdateFootballPoolUseCase updateFootballPoolUseCase;
    private final DeleteFootballPoolUseCase softDeleteFootballPoolUseCase;

    @PostMapping("/create")
    public ResponseEntity<FootballPool> create(@RequestBody FootballPoolDTO request) {

        FootballPool footballPool = createUseCase.execute(
                request.getName(),
                request.getState(),
                request.getTournamentUuid(),
                request.getChampionsWinPoints(),
                request.getSecondPlaceWinPoints(),
                request.getThirdPlaceWinPoints(),
                request.getWinnerTeamWinPoints(),
                request.getTotalYellowCardsWinPoints(),
                request.getTotalRedCardsWinPoints(),
                request.getTotalLocalGoalsWinPoints(),
                request.getTotalVisitingGoalWinPoints()
        );

        return ResponseEntity.ok(footballPool);
    }

    @GetMapping("/get")
    public ResponseEntity<List<FootballPool>> getAll() {
        List<FootballPool> footballPools = getFooballPoolsUseCase.execute();
        return ResponseEntity.ok(footballPools);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<FootballPool> getById(@PathVariable UUID uuid) {
        FootballPool footballPool = getFootballPoolByIdUseCase.execute(uuid);
        return ResponseEntity.ok(footballPool);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<FootballPool> update(@PathVariable UUID uuid, @RequestBody FootballPoolDTO request) {
        FootballPool updated = updateFootballPoolUseCase.execute(uuid, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<FootballPool> delete(@PathVariable UUID uuid) {
        FootballPool deleted = softDeleteFootballPoolUseCase.execute(uuid);
        return ResponseEntity.ok(deleted);
    }

}