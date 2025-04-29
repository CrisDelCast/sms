package co.edu.icesi.pf.domain.model.gateways.notifications;

public interface EmailService {

    boolean sendOtpToEmail(String recipient, String otp);

    boolean sendNotificationEmail(String recipient, String subject, String body);
}
