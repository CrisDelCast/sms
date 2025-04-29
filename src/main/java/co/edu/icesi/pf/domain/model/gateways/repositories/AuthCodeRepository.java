package co.edu.icesi.pf.domain.model.gateways.repositories;

import java.util.Optional;

import co.edu.icesi.pf.domain.model.records.AuthCode;

public interface AuthCodeRepository {
    void save(AuthCode authCode);
    Optional<AuthCode> findByUserId(String userId);
    void delete(String userId);
}
