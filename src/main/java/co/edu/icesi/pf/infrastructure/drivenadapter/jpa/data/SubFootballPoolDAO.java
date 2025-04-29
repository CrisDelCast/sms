package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

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
@Table(name = "sub_football_pool")
public class SubFootballPoolDAO {
    @Id
    @UuidGenerator
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_updated", nullable = false)
    private LocalDate lastUpdated;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "football_pool_uuid", nullable = false)
    private String footballPoolUuid;
}
