package co.edu.icesi.pf.domain.usecase.prize;

import co.edu.icesi.pf.domain.model.gateways.repositories.PrizeRepository;
import co.edu.icesi.pf.domain.model.records.Prize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeletePrizeUseCase {

    private final PrizeRepository prizeRepository;

    public Prize execute(UUID uuid) {
        return prizeRepository.softDelete(uuid);
    }
}