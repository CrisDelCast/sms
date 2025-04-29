package co.edu.icesi.pf.domain.model.records;

import java.util.UUID;

public record Prize(
        UUID uuid,
        String name,
        String image,
        String organizationUuid,
        String state
) {

    public Prize(
            String name,
            String image,
            String organizationUuid,
            String state
    ) {
        this(null, name, image, organizationUuid, state);
    }
}