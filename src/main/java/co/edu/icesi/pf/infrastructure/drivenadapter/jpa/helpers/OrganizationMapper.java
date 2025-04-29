package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.Organization;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.OrganizationDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    Organization toDomain(OrganizationDAO dao);
    OrganizationDAO toEntity(Organization organization);
}