package co.edu.icesi.pf.infrastructure.entrypoint.apirest.subfootballpool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SubFootballPoolDTO {
    private String id;
    private String name;
    private LocalDate creationDate;
    private LocalDate lastUpdated;
    private String url;
    private String footballPoolUuid;

}
