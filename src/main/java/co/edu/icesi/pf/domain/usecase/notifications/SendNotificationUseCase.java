package co.edu.icesi.pf.domain.usecase.notifications;

import co.edu.icesi.pf.domain.model.gateways.services.NotificationService;

public class SendNotificationUseCase {
    private final NotificationService notificationService;

    public SendNotificationUseCase(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void execute(String recipient, String message) {
        notificationService.sendNotification(recipient, message);
    }
}