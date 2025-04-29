package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.Role;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.RoleDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(RoleDAO dao);
    RoleDAO toEntity(Role role);
}
