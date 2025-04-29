package co.edu.icesi.pf.domain.model.gateways.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.MailConfig;

@Service
public class MailService {

    private final MailConfig mailConfig;

    @Autowired
    public MailService(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

}