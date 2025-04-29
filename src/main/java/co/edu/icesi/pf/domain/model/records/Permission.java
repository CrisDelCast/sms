package co.edu.icesi.pf.domain.model.records;

import java.util.Date;

public record Permission (
    String uuid,
    String name,
    Date created_at,
    Date updated_at,
    String state
) {}