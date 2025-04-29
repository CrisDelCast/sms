package co.edu.icesi.pf.domain.usecase.footballpool;


import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.FootballPool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CreateFootballPoolUseCase {

    private final FootballPoolRepository footballPoolRepository;

    public FootballPool execute(String name, String state, String tournamentUuid, Integer championsWinPoints, Integer secondPlaceWinPoints, Integer thirdPlaceWinPoints,
                                Integer winnerTeamWinPoints, Integer totalYellowCardsWinPoints, Integer totalRedCardsWinPoints, Integer totalLocalGoalsWinPoints, Integer totalVisitingGoalWinPoints) {

        FootballPool footballPool = new FootballPool(name, state, tournamentUuid, championsWinPoints,secondPlaceWinPoints,thirdPlaceWinPoints,winnerTeamWinPoints,totalYellowCardsWinPoints,
                totalRedCardsWinPoints,totalLocalGoalsWinPoints,totalVisitingGoalWinPoints);

        return footballPoolRepository.save(footballPool);
    }
}

