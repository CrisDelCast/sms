package co.edu.icesi.pf.domain.model.records;

import java.time.LocalDate;

public record SubFootballPool(
        String name,
        LocalDate creationDate,
        LocalDate lastUpdated,
        String url,
        String footballPoolUuid
) {
}
