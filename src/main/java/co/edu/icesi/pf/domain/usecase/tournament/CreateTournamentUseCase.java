package co.edu.icesi.pf.domain.usecase.tournament;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import co.edu.icesi.pf.domain.model.records.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CreateTournamentUseCase {
    private final TournamentRepository tournamentRepository;

    public Tournament execute(String name, String organizationUuid, LocalDate startingDate, LocalDate endingDate, String state) {
        Tournament tournament = new Tournament( name, organizationUuid, startingDate, endingDate, state);
        return tournamentRepository.save(tournament);
    }
}

