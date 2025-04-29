package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tournament_phase")
public class TournamentPhaseDAO {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "tournament_uuid", nullable = false)
    private String tournamentUuid;

    @Column(name = "state", nullable = false)
    private String state;

}
