package co.edu.icesi.pf.domain.usecase.prize;

import co.edu.icesi.pf.domain.model.gateways.repositories.PrizeRepository;
import co.edu.icesi.pf.domain.model.records.Prize;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.prize.dto.PrizeDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdatePrizeUseCase {

    private final PrizeRepository prizeRepository;

    public Prize execute(UUID uuid, PrizeDTO dto) {
        Prize existing = prizeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Prize not found"));

        Prize updated = new Prize(
                dto.getName(),
                dto.getImage(),
                dto.getOrganizationUuid(),
                dto.getState()
        );

        return prizeRepository.update(uuid, updated);
    }
}