package co.edu.icesi.pf.infrastructure.entrypoint.apirest.team.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private String name;
    private String nameAbbreviation;
    private String teamFlag;
    private Integer points;
}