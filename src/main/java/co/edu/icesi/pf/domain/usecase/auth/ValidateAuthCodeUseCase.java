package co.edu.icesi.pf.domain.usecase.auth;

import java.time.LocalDateTime;

import org.springframework.security.core.token.TokenService;

import co.edu.icesi.pf.domain.model.gateways.repositories.AuthCodeRepository;
import co.edu.icesi.pf.domain.model.records.AuthCode;


public class ValidateAuthCodeUseCase {
    private final AuthCodeRepository authCodeRepository;

    public ValidateAuthCodeUseCase(AuthCodeRepository authCodeRepository) {
        this.authCodeRepository = authCodeRepository;
    }

    public String execute(String userId, String inputCode) {
        java.util.Optional<AuthCode> authCodeOpt = authCodeRepository.findByUserId(userId);

        if (authCodeOpt.isEmpty()) {
            throw new RuntimeException("Código no encontrado.");
        }

        AuthCode authCode = authCodeOpt.get();
        if (!authCode.code().equals(inputCode) || LocalDateTime.now().isAfter(authCode.expiration())) {
            throw new RuntimeException("Código inválido o expirado.");
        }

        authCodeRepository.delete(userId);

        return userId;
    }
}
