package co.edu.icesi.pf.infrastructure.entrypoint.apirest.organization.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrganizationDTO {
    private String nit;
    private String name;
    private String email;
    private String logo;
    private String phone;
    private String themes;
    private String banner;
    private String state;
}