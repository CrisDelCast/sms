package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthCodeEntity {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expiration", nullable = false)
    private LocalDateTime expiration;
}