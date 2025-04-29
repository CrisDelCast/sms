package co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournamentPhase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TournamentPhaseDTO {

    private String name;
    private String tournamentUuid;
    private String state;
}
