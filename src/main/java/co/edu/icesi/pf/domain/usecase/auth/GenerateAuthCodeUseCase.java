package co.edu.icesi.pf.domain.usecase.auth;

import java.time.LocalDateTime;
import java.util.Random;

import co.edu.icesi.pf.domain.model.gateways.services.NotificationService;
import co.edu.icesi.pf.domain.model.records.AuthCode;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.AuthCodeRepositoryImpl;

public class GenerateAuthCodeUseCase {
    private final AuthCodeRepositoryImpl authCodeRepository;
    private final NotificationService notificationService;

    public GenerateAuthCodeUseCase(AuthCodeRepositoryImpl authCodeRepository
    ,NotificationService notificationService
    ) {
        this.authCodeRepository = authCodeRepository;
        this.notificationService = notificationService;
    }

    public void execute(String userId, String contact) {
        String code = String.valueOf(new Random().nextInt(999999)); 
        AuthCode authCode = new AuthCode(userId, code, LocalDateTime.now().plusMinutes(5));

        authCodeRepository.save(authCode);
        notificationService.sendNotification(contact, code);
    }
}