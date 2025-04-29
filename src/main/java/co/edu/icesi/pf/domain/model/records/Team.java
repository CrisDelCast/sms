package co.edu.icesi.pf.domain.model.records;

import java.util.UUID;

public record Team(
        UUID uuid,
        String name,
        String nameAbbreviation,
        String teamFlag,
        Integer points
) {

    public Team(
            String name,
            String nameAbbreviation,
            String teamFlag,
            Integer points
    ) {
        this(null, name, nameAbbreviation, teamFlag, points);
    }
}