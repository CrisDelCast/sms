package co.edu.icesi.pf.domain.usecase.footballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.FootballPool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetFootballPoolByIdUseCase {

    private final FootballPoolRepository footballPoolRepository;

     public FootballPool execute(UUID uuid) {
            return footballPoolRepository.findByUuid(uuid)
                    .orElseThrow(() -> new IllegalArgumentException("FootballPool not found for id: " + uuid));
    }
}
