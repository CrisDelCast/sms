package co.edu.icesi.pf.domain.usecase.tournamentPhase;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentPhaseRepository;
import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateTournamentPhaseUseCase {

    private final TournamentPhaseRepository tournamentPhaseRepository;

    public TournamentPhase execute(String name, String tournamentUuid, String state) {

        TournamentPhase tournamentPhase = new TournamentPhase(name, tournamentUuid, state);
        return tournamentPhaseRepository.save(tournamentPhase);
    }
}
