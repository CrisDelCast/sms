package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data;

import co.edu.icesi.pf.domain.model.records.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;


@Data
@Entity
@Table(name = "user_account")
public class UserDAO {
    @Id
    @UuidGenerator
    private String uuid;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "have_fa", nullable = false)
    private boolean have_fa;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    @Column(name = "role_uuid")
    private String role_uuid;

    @Column(name = "otp")
    private String otp;

    @Column(name = "state", nullable = false)
    private String state;

    public UserDAO() {}

    public UserDAO(String id, String email, String phone, int age, boolean have_fa, String name, String nickname, String password, String photo, Date created_at, Date updated_at, String role_uuid, String otp, String state) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.have_fa = have_fa;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.photo= photo;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.role_uuid = role_uuid;
        this.otp = otp;
        this.state = state;
    }


}