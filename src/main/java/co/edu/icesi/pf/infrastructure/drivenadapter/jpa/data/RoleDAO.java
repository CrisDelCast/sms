package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@Entity
@Table(name = "role")
public class RoleDAO {

    @Id
    @UuidGenerator
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    @Column(name = "state", nullable = false)
    private String state;

    public RoleDAO() {}

    public RoleDAO(String name, String description, Date created_at, Date updated_at, String state) {
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.state = state;
    }

}
