package co.edu.icesi.pf.domain.model.gateways.services;

public interface NotificationService {
    void sendNotification(String recipient, String message);
    String generateOtp();
     
}