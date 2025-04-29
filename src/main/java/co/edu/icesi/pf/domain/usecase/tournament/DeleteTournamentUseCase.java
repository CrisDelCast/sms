package co.edu.icesi.pf.domain.usecase.tournament;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteTournamentUseCase {
    private final TournamentRepository tournamentRepository;

    public void execute(String uuid) {
        tournamentRepository.delete(uuid);
    }
} 