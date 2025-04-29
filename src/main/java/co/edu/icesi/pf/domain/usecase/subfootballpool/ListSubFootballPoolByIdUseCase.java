package co.edu.icesi.pf.domain.usecase.subfootballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.SubFootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.SubFootballPool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ListSubFootballPoolByIdUseCase {
    private final SubFootballPoolRepository subFootballPoolRepository;

    public SubFootballPool execute(String id) {
        return subFootballPoolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("SubFootballPool not found for id: " + id));
    }
}
