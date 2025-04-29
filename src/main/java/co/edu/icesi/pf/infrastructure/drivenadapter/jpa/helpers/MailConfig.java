package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class MailConfig {

    private final String host = EnvConfig.get("MAIL_HOST");
    private final int port = Integer.parseInt(EnvConfig.get("MAIL_PORT"));
    private final String username = EnvConfig.get("MAIL_USERNAME");
    private final String password = EnvConfig.get("MAIL_PASSWORD");
    private final boolean smtpAuth = Boolean.parseBoolean(EnvConfig.get("MAIL_SMTP_AUTH"));
    private final boolean starttlsEnable = Boolean.parseBoolean(EnvConfig.get("MAIL_STARTTLS_ENABLE"));

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSmtpAuth() {
        return smtpAuth;
    }

    public boolean isStarttlsEnable() {
        return starttlsEnable;
    }  
}
