package co.edu.icesi.pf.domain.usecase.subfootballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.SubFootballPoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteSubFootballPoolUseCase {
    private final SubFootballPoolRepository subFootballPoolRepository;

    public boolean execute(String id) {
        return subFootballPoolRepository.delete(id);
    }
}
