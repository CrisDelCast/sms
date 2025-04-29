package co.edu.icesi.pf.domain.model.records;

import java.time.LocalDate;

public record Tournament(String uuid, String name, String organizationUuid, LocalDate startingDate, LocalDate endingDate, String state) {

    public Tournament(String name, String organizationUuid, LocalDate startingDate, LocalDate endingDate, String state) {
        this(null, name, organizationUuid, startingDate, endingDate, state);
    }
}

