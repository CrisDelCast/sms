package co.edu.icesi.pf.domain.usecase.subfootballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.SubFootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.SubFootballPool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CreateSubFootballPoolUseCase {
    private final SubFootballPoolRepository subFootballPoolRepository;

    public SubFootballPool execute(String name, LocalDate creationDate, LocalDate lastUpdated, String url, String footballPoolUuid) {
        SubFootballPool subFootballPool = new SubFootballPool(name, creationDate, lastUpdated, url, footballPoolUuid);
        return subFootballPoolRepository.save(subFootballPool);
    }
}
