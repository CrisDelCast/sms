package co.edu.icesi.pf.domain.usecase.tournamentPhase;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentPhaseRepository;
import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteTournamentPhaseUseCase {

    private final TournamentPhaseRepository tournamentPhaseRepository;

    public TournamentPhase execute(UUID uuid) {
        return tournamentPhaseRepository.softDelete(uuid);
    }
}
