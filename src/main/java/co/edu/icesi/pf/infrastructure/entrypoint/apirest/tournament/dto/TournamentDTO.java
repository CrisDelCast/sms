package co.edu.icesi.pf.infrastructure.entrypoint.apirest.tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TournamentDTO {
    private String name;
    private String organizationUuid;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String state;
}

