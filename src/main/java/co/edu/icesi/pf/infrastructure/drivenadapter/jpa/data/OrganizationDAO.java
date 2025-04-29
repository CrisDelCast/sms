package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

import co.edu.icesi.pf.domain.model.records.Organization;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;


@Data
@Entity
@Table(name = "organization")
public class OrganizationDAO {
    @Id
    @UuidGenerator
    private String uuid;

    @Column(name = "nit", nullable = false)
    private String nit;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "logo")
    private String logo;

    @Column(name = "phone")
    private String phone;

    @Column(name = "themes")
    private String themes;

    @Column(name = "banner")
    private String banner;

    @Column(name = "state", nullable = false)
    private String state;

    public OrganizationDAO() {}

    public OrganizationDAO(String nit, String name, String email, String logo, String phone, String themes, String banner, String state) {
        this.nit = nit;
        this.name = name;
        this.email = email;
        this.logo = logo;
        this.phone = phone;
        this.themes = themes;
        this.banner = banner;
        this.state = state;
    }


}