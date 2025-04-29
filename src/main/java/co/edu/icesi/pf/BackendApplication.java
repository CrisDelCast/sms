package co.edu.icesi.pf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

import co.edu.icesi.pf.domain.model.gateways.services.NotificationService;
import co.edu.icesi.pf.domain.usecase.notifications.GenerateOtpUseCase;
import co.edu.icesi.pf.domain.usecase.notifications.SendNotificationUseCase;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.EnvConfig;
import co.edu.icesi.pf.infrastructure.drivenadapter.notifications.NotificationServiceAdapter;

// TODO: Configure Spring Security with JWT then disable this exclusion
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class
})
@ComponentScan("co.edu.icesi.pf")
@EnableScheduling
public class BackendApplication {

    public static void main(String[] args) {
        System.setProperty("MAIL_HOST", EnvConfig.get("MAIL_HOST"));
        System.setProperty("MAIL_PORT", EnvConfig.get("MAIL_PORT"));
        System.setProperty("MAIL_USERNAME", EnvConfig.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", EnvConfig.get("MAIL_PASSWORD"));
        System.setProperty("MAIL_SMTP_AUTH", EnvConfig.get("MAIL_SMTP_AUTH"));
        System.setProperty("MAIL_STARTTLS_ENABLE", EnvConfig.get("MAIL_STARTTLS_ENABLE"));
        SpringApplication.run(BackendApplication.class, args);
        
    }

    @Bean
    public SendNotificationUseCase sendNotificationUseCase(NotificationService notificationService) {
        return new SendNotificationUseCase(notificationService);
    }


    @Bean
    @Primary
    public NotificationService notificationService(JavaMailSender mailSender) {
        return new NotificationServiceAdapter(mailSender);
    }

}