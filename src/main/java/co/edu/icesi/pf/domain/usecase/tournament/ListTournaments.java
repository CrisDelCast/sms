package co.edu.icesi.pf.domain.usecase.tournament;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import co.edu.icesi.pf.domain.model.records.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListTournaments {
    private final TournamentRepository tournamentRepository;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }
}
