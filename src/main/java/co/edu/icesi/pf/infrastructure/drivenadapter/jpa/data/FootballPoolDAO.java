package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

import co.edu.icesi.pf.domain.model.records.FootballPool;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Football_Pool")
public class FootballPoolDAO {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "STATE", nullable = false)
    private String state;

    @Column(name = "TOURNAMENT_UUID", nullable = false)
    private String tournamentUuid;

    @Column(name = "CHAMPIONS_WIN_POINTS", nullable = false)
    private Integer championsWinPoints;

    @Column(name = "SECOND_PLACE_WIN_POINTS", nullable = false)
    private Integer secondPlaceWinPoints;

    @Column(name = "THIRD_PLACE_WIN_POINTS", nullable = false)
    private Integer thirdPlaceWinPoints;

    @Column(name = "WINNER_TEAM_WIN_POINTS", nullable = false)
    private Integer winnerTeamWinPoints;

    @Column(name = "TOTAL_YELLOW_CARDS_WIN_POINTS", nullable = false)
    private Integer totalYellowCardsWinPoints;

    @Column(name = "TOTAL_RED_CARDS_WIN_POINTS", nullable = false)
    private Integer totalRedCardsWinPoints;

    @Column(name = "TOTAL_LOCAL_GOALS_WIN_POINTS", nullable = false)
    private Integer totalLocalGoalsWinPoints;

    @Column(name = "TOTAL_VISITING_GOAL_WIN_POINTS", nullable = false)
    private Integer totalVisitingGoalWinPoints;

}
