package co.edu.icesi.pf.domain.model.records;


import java.time.LocalDateTime;

public record AuthCode(String userId, String code, LocalDateTime expiration) {}
