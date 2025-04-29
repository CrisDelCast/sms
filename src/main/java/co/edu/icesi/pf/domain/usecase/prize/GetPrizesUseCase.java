package co.edu.icesi.pf.domain.usecase.prize;

import co.edu.icesi.pf.domain.model.gateways.repositories.PrizeRepository;
import co.edu.icesi.pf.domain.model.records.Prize;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPrizesUseCase {

    private final PrizeRepository prizeRepository;

    public List<Prize> execute() {
        return prizeRepository.findAll();
    }
}