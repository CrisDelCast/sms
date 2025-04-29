package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.User;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.UserDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserDAO dao);
    UserDAO toEntity(User user);
}