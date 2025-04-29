package co.edu.icesi.pf.domain.model.gateways.notifications;

public interface OtpService {
    String generateOtp(String recipient);
    boolean validateOtp(String recipient, String otp);
}
