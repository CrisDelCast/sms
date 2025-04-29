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
@Table(name = "permissions")
public class PermissionDAO {
    @Id
    @UuidGenerator
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    @Column(name = "state", nullable = false)
    private String state;

    public PermissionDAO() {}

    public PermissionDAO(String name, Date created_at, Date updated_at, String state) {
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.state = state;
    }
}
