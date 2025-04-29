package co.edu.icesi.pf.domain.usecase.team;

import co.edu.icesi.pf.domain.model.gateways.repositories.TeamRepository;
import co.edu.icesi.pf.domain.model.records.Team;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.team.dto.TeamDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateTeamUseCase {

    private final TeamRepository teamRepository;

    public Team execute(UUID uuid, TeamDTO dto) {
        Team existing = teamRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Team updated = new Team(
                dto.getName(),
                dto.getNameAbbreviation(),
                dto.getTeamFlag(),
                dto.getPoints()
        );

        return teamRepository.update(uuid, updated);
    }
}