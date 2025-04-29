package co.edu.icesi.pf.infrastructure.drivenadapter.notifications;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import co.edu.icesi.pf.domain.model.gateways.services.NotificationService;
import jakarta.mail.internet.MimeMessage;

import java.security.SecureRandom;

@Service
public class  NotificationServiceAdapter implements NotificationService {
    private final JavaMailSender mailSender;

    public NotificationServiceAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(String recipient, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(recipient);
            mailMessage.setSubject("Notification");
            mailMessage.setText(message);
            mailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
