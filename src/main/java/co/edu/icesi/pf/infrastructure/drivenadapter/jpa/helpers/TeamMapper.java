package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.Team;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TeamDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toDomain(TeamDAO dao);
    TeamDAO toEntity(Team team);
}