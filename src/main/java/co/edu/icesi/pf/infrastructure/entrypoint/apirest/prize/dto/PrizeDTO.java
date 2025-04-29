package co.edu.icesi.pf.infrastructure.entrypoint.apirest.prize.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeDTO {
    private String name;
    private String image;
    private String organizationUuid;
    private String state;
}