package co.edu.icesi.pf.domain.usecase.footballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.FootballPool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteFootballPoolUseCase {

    private final FootballPoolRepository footballPoolRepository;

    public FootballPool execute(UUID uuid) {
        return footballPoolRepository.softDelete(uuid);
    }
}

