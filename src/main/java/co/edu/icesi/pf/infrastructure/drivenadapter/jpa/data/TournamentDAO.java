package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

import co.edu.icesi.pf.domain.model.records.Tournament;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tournament")
public class TournamentDAO {
    @Id
    @UuidGenerator
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "organization_uuid", nullable = true)
    private String organizationUuid;

    @Column(name = "starting_date", nullable = false)
    private LocalDate startingDate;

    @Column(name = "ending_date", nullable = false)
    private LocalDate endingDate;

    @Column(name = "state", nullable = false)
    private String state;
}
