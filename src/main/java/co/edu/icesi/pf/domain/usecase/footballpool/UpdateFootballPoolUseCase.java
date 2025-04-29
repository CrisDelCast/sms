package co.edu.icesi.pf.domain.usecase.footballpool;

import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.FootballPool;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.footballpool.dto.FootballPoolDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateFootballPoolUseCase {

    private final FootballPoolRepository footballPoolRepository;

    public FootballPool execute(UUID uuid, FootballPoolDTO dto) {

        FootballPool existing = footballPoolRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Football Pool not found"));

        FootballPool updated = new FootballPool(
                dto.getName(),
                dto.getState(),
                dto.getTournamentUuid(),
                dto.getChampionsWinPoints(),
                dto.getSecondPlaceWinPoints(),
                dto.getThirdPlaceWinPoints(),
                dto.getWinnerTeamWinPoints(),
                dto.getTotalYellowCardsWinPoints(),
                dto.getTotalRedCardsWinPoints(),
                dto.getTotalLocalGoalsWinPoints(),
                dto.getTotalVisitingGoalWinPoints()
        );

        return footballPoolRepository.update(uuid, updated);
    }
}
