package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.Permission;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.PermissionDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toDomain(PermissionDAO dao);
    PermissionDAO toEntity(Permission permission);
}
