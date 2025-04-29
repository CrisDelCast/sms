package co.edu.icesi.pf.domain.usecase.prize;

import co.edu.icesi.pf.domain.model.gateways.repositories.PrizeRepository;
import co.edu.icesi.pf.domain.model.records.Prize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreatePrizeUseCase {

    private final PrizeRepository prizeRepository;

    public Prize execute(String name, String image, String organizationUuid, String state) {
        Prize prize = new Prize(name, image, organizationUuid, state);
        return prizeRepository.save(prize);
    }
}