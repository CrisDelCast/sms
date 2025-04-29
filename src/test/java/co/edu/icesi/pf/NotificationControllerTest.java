package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.notifications.EmailService;
import co.edu.icesi.pf.domain.model.gateways.notifications.EmailServiceImpl;
import co.edu.icesi.pf.domain.model.gateways.notifications.OtpService;
import co.edu.icesi.pf.domain.model.gateways.notifications.OtpServiceImpl;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.notifications.NotificationController;
import io.github.cdimascio.dotenv.Dotenv;
import co.edu.icesi.pf.domain.usecase.notifications.SendNotificationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 

class NotificationControllerTest {

    @Mock
    private SendNotificationUseCase sendNotificationUseCase;

    @Mock
    private OtpServiceImpl otpService;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private NotificationController notificationController;

    private final String recipient = "rocosa2003@gmail.com";
    private final String otp = "123456";

    @BeforeEach
    void setUp() {
    }

    static {
        
        Dotenv dotenv = Dotenv.load();
        System.setProperty("MAIL_HOST", dotenv.get("MAIL_HOST"));
        System.setProperty("MAIL_PORT", dotenv.get("MAIL_PORT"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
    }

    @Test
    void sendOtp_shouldReturnOk_whenEmailIsSent() {
        when(otpService.generateOtp(recipient)).thenReturn(otp);
        when(emailService.sendOtpToEmail(recipient, otp)).thenReturn(true);

        ResponseEntity<Map<String, String>> response = notificationController.sendOtp(recipient);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("message", "OTP enviado exitosamente a " + recipient);

        verify(otpService).generateOtp(recipient);
        verify(emailService).sendOtpToEmail(recipient, otp);
    }

    @Test
    void sendOtp_shouldReturnInternalServerError_whenEmailFailsToSend() {
        when(otpService.generateOtp(recipient)).thenReturn(otp);
        when(emailService.sendOtpToEmail(recipient, otp)).thenReturn(false);

        ResponseEntity<Map<String, String>> response = notificationController.sendOtp(recipient);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).containsEntry("error", "No se pudo enviar el OTP");

        verify(otpService).generateOtp(recipient);
        verify(emailService).sendOtpToEmail(recipient, otp);
    }

    @Test
    void validateOtp_shouldReturnOk_whenOtpIsValid() {

        when(otpService.validateOtp(recipient, otp)).thenReturn(true);

        ResponseEntity<Map<String, String>> response = notificationController.validateOtp(recipient, otp);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsEntry("message", "OTP válido");

        verify(otpService).validateOtp(recipient, otp);
    }

    @Test
    void validateOtp_shouldReturnUnauthorized_whenOtpIsInvalid() {
        when(otpService.validateOtp(recipient, otp)).thenReturn(false);

        ResponseEntity<Map<String, String>> response = notificationController.validateOtp(recipient, otp);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).containsEntry("error", "OTP inválido o expirado");

        verify(otpService).validateOtp(recipient, otp);
    }
}
