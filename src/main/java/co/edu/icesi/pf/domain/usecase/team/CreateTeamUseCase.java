package co.edu.icesi.pf.domain.usecase.team;

import co.edu.icesi.pf.domain.model.gateways.repositories.TeamRepository;
import co.edu.icesi.pf.domain.model.records.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateTeamUseCase {

    private final TeamRepository teamRepository;

    public Team execute(String name, String nameAbbreviation, String teamFlag, Integer points) {
        Team team = new Team(name, nameAbbreviation, teamFlag, points);
        return teamRepository.save(team);
    }
}