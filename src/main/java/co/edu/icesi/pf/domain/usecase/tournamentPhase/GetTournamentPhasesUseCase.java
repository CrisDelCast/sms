package co.edu.icesi.pf.domain.usecase.tournamentPhase;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentPhaseRepository;
import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetTournamentPhasesUseCase {

    private final TournamentPhaseRepository tournamentPhaseRepository;

    public List<TournamentPhase> execute() {
        return tournamentPhaseRepository.findAll();
    }
}
