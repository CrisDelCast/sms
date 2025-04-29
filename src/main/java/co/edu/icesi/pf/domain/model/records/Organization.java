package co.edu.icesi.pf.domain.model.records;

public record Organization(
        String nit,
        String name,
        String email,
        String logo,
        String phone,
        String themes,
        String banner,
        String state
) {}