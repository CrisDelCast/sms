package co.edu.icesi.pf.domain.usecase.team;

import co.edu.icesi.pf.domain.model.gateways.repositories.TeamRepository;
import co.edu.icesi.pf.domain.model.records.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetTeamsUseCase {

    private final TeamRepository teamRepository;

    public List<Team> execute() {
        return teamRepository.findAll();
    }
}