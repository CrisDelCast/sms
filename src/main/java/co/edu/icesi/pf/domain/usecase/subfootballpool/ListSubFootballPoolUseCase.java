package co.edu.icesi.pf.domain.usecase.subfootballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.SubFootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.SubFootballPool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListSubFootballPoolUseCase {
    private final SubFootballPoolRepository subFootballPoolRepository;

    public List<SubFootballPool> execute() {
        return subFootballPoolRepository.findAll();
    }
}
