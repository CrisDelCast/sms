package co.edu.icesi.pf.domain.model.gateways.notifications;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@Primary
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendOtpToEmail(String recipient, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(recipient);
            helper.setSubject("Tu código de seguridad");
            
            String body = "<html><body>"
                    + "<h2>Código de Seguridad</h2>"
                    + "<p>Tu código OTP es: <b>" + otp + "</b></p>"
                    + "<p>Este código expira en 5 minutos.</p>"
                    + "<p>Si no solicitaste este código, ignora este mensaje.</p>"
                    + "</body></html>";
            
            helper.setText(body, true);
            mailSender.send(message);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendNotificationEmail(String recipient, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(body); 

            mailSender.send(message); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
