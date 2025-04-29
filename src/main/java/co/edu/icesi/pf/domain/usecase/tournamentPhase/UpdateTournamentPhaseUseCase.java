package co.edu.icesi.pf.domain.usecase.tournamentPhase;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentPhaseRepository;
import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateTournamentPhaseUseCase {

    private final TournamentPhaseRepository tournamentPhaseRepository;

    public TournamentPhase execute(UUID uuid, String name, String tournamentUuid, String state) {

        TournamentPhase existing = tournamentPhaseRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Tournament phase not found"));

        TournamentPhase tournamentPhase = new TournamentPhase(name, tournamentUuid, state);
        return tournamentPhaseRepository.update(uuid, tournamentPhase);
    }
}
