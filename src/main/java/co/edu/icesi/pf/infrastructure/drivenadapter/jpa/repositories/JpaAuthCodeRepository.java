package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;



import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters.entities.AuthCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAuthCodeRepository extends JpaRepository<AuthCodeEntity, String> {
    Optional<AuthCodeEntity> findByUserId(String userId);
}