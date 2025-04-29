package co.edu.icesi.pf.domain.usecase.team;

import co.edu.icesi.pf.domain.model.gateways.repositories.TeamRepository;
import co.edu.icesi.pf.domain.model.records.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteTeamUseCase {

    private final TeamRepository teamRepository;

    public Team execute(UUID uuid) {
        return teamRepository.softDelete(uuid);
    }
}