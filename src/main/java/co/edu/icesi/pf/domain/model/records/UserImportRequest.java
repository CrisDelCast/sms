package co.edu.icesi.pf.domain.model.records;

public record UserImportRequest(
        String id,
        String email,
        String phone,
        int age,
        boolean haveFa,
        String name,
        String nickname,
        String password,
        String photo,
        String state,
        String roleUuid,
        String otp
) {}
