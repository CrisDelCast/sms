package co.edu.icesi.pf.domain.model.records;


import java.util.UUID;

public record FootballPool(
        UUID uuid,
        String name,
        String state,
        String tournamentUuid,
        Integer championsWinPoints,
        Integer secondPlaceWinPoints,
        Integer thirdPlaceWinPoints,
        Integer winnerTeamWinPoints,
        Integer totalYellowCardsWinPoints,
        Integer totalRedCardsWinPoints,
        Integer totalLocalGoalsWinPoints,
        Integer totalVisitingGoalWinPoints

) {

    public FootballPool (
            String name,
            String state,
            String tournamentUuid,
            Integer championsWinPoints,
            Integer secondPlaceWinPoints,
            Integer thirdPlaceWinPoints,
            Integer winnerTeamWinPoints,
            Integer totalYellowCardsWinPoints,
            Integer totalRedCardsWinPoints,
            Integer totalLocalGoalsWinPoints,
            Integer totalVisitingGoalWinPoints
    ) {
        this(null, name, state, tournamentUuid, championsWinPoints, secondPlaceWinPoints, thirdPlaceWinPoints, winnerTeamWinPoints, totalYellowCardsWinPoints, totalRedCardsWinPoints, totalLocalGoalsWinPoints, totalVisitingGoalWinPoints);
    }
}

