package co.edu.icesi.pf.domain.usecase.tournament;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import co.edu.icesi.pf.domain.model.records.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListTournamentsByState {
    private final TournamentRepository tournamentRepository;

    public List<Tournament> getAllTournamentsByState(String state) {
        return tournamentRepository.findAllByState(state);
    }
}
