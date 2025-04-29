package co.edu.icesi.pf.infrastructure.entrypoint.apirest.notifications;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.pf.domain.model.gateways.notifications.EmailServiceImpl;
import co.edu.icesi.pf.domain.model.gateways.notifications.OtpServiceImpl;
import co.edu.icesi.pf.domain.usecase.notifications.SendNotificationUseCase;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final SendNotificationUseCase sendNotificationUseCase;
    private final OtpServiceImpl otpService;
    private final EmailServiceImpl emailService;

    public NotificationController(SendNotificationUseCase sendNotificationUseCase, OtpServiceImpl otpService, EmailServiceImpl emailService) {
        this.sendNotificationUseCase = sendNotificationUseCase;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @GetMapping("/otp/send")
    public ResponseEntity<Map<String, String>> sendOtp(@RequestParam(name = "recipient") String recipient) {
        String otp = otpService.generateOtp(recipient);
        boolean sent = emailService.sendOtpToEmail(recipient, otp);

        if (sent) {
            return ResponseEntity.ok(Collections.singletonMap("message", "OTP enviado exitosamente a " + recipient));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "No se pudo enviar el OTP"));
        }
    }

    @GetMapping("/otp/validate")
    public ResponseEntity<Map<String, String>> validateOtp(
            @RequestParam(name = "recipient") String recipient,
            @RequestParam(name = "otp") String otp) {

            boolean isValid = otpService.validateOtp(recipient, otp);

            if (isValid) {
                return ResponseEntity.ok(Collections.singletonMap("message", "OTP válido"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "OTP inválido o expirado"));
            }
        }

        @PostMapping("/send")
        public ResponseEntity<Map<String, String>> sendNotification(@RequestBody NotificationRequest notificationRequest) {
            boolean isSent = emailService.sendNotificationEmail(
                    notificationRequest.getRecipient(),
                    notificationRequest.getSubject(),
                    notificationRequest.getBody()
            );
        
            if (isSent) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Correo enviado con éxito."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("error", "Hubo un problema al enviar el correo."));
            }
        }
   
    }
