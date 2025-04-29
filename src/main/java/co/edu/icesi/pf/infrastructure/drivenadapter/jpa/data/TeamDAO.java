package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

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
@Table(name = "team")
public class TeamDAO {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NAME_ABBREVIATION", nullable = false)
    private String nameAbbreviation;

    @Column(name = "TEAM_FLAG", nullable = false)
    private String teamFlag;

    @Column(name = "POINTS", nullable = false)
    private Integer points;
}