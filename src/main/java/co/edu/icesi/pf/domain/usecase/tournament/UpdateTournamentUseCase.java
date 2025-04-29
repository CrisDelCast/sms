package co.edu.icesi.pf.domain.usecase.tournament;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import co.edu.icesi.pf.domain.model.records.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UpdateTournamentUseCase {
    private final TournamentRepository tournamentRepository;

    public Tournament execute(String uuid, String name, String organizationUuid, LocalDate startingDate, LocalDate endingDate, String state) {
        Tournament tournament = new Tournament(uuid, name, organizationUuid, startingDate, endingDate, state);
        return tournamentRepository.update(tournament);
    }
}