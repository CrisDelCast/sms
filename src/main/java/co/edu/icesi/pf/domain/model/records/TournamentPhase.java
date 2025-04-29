package co.edu.icesi.pf.domain.model.records;

import java.util.UUID;

public record TournamentPhase(UUID uuid, String name, String tournamentUuid, String state) {

    public TournamentPhase(String name, String tournamentUuid, String state) {
        this(null, name, tournamentUuid, state);
    }
}
