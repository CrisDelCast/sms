package co.edu.icesi.pf.infrastructure.entrypoint.apirest.footballpool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FootballPoolDTO {

    private String name;
    private String state;
    private String tournamentUuid;
    private Integer championsWinPoints;
    private Integer secondPlaceWinPoints;
    private Integer thirdPlaceWinPoints;
    private Integer winnerTeamWinPoints;
    private Integer totalYellowCardsWinPoints;
    private Integer totalRedCardsWinPoints;
    private Integer totalLocalGoalsWinPoints;
    private Integer totalVisitingGoalWinPoints;
}

