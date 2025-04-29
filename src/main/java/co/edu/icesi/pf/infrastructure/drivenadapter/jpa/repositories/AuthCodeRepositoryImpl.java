package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;

import co.edu.icesi.pf.domain.model.gateways.repositories.AuthCodeRepository;
import co.edu.icesi.pf.domain.model.records.AuthCode;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters.entities.AuthCodeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthCodeRepositoryImpl implements AuthCodeRepository {

    private final JpaAuthCodeRepository jpaAuthCodeRepository;

    @Value("${authcode.expiration.default}")
    private String defaultExpirationStr;


    @Override
    public void save(AuthCode authCode) {
        AuthCodeEntity entity = new AuthCodeEntity(
                authCode.userId(),
                authCode.code(),
                authCode.expiration() != null ? authCode.expiration() : defaultExpiration()
        );
        jpaAuthCodeRepository.save(entity);
    }

    @Override
    public Optional<AuthCode> findByUserId(String userId) {
        return jpaAuthCodeRepository.findByUserId(userId)
                .map(entity -> new AuthCode(entity.getUserId(), entity.getCode(), entity.getExpiration()));
    }

    @Override
    public void delete(String userId) {
        jpaAuthCodeRepository.deleteById(userId);
    }
    private LocalDateTime defaultExpiration() {
        long expirationMinutes;
        try {
            expirationMinutes = Long.parseLong(defaultExpirationStr);
        } catch (NumberFormatException | NullPointerException e) {
            expirationMinutes = 15;
        }
        return LocalDateTime.now().plusMinutes(expirationMinutes);
    }
    
}