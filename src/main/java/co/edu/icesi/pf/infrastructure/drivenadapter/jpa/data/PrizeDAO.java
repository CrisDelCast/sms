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
@Table(name = "prize")
public class PrizeDAO {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IMAGE", nullable = false)
    private String image;

    @Column(name = "ORGANIZATION_UUID", nullable = false)
    private String organizationUuid;

    @Column(name = "STATE", nullable = false)
    private String state;
}