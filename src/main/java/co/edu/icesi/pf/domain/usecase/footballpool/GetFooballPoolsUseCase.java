package co.edu.icesi.pf.domain.usecase.footballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.FootballPool;
import co.edu.icesi.pf.domain.model.records.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetFooballPoolsUseCase {

    private final FootballPoolRepository footballPoolRepository;

    public List<FootballPool> execute() {
        return footballPoolRepository.findAll();
    }

}
