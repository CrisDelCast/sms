package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.FootballPool;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.FootballPoolDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FootballPoolMapper {

    FootballPool toDomain(FootballPoolDAO dao);
    FootballPoolDAO toEntity(FootballPool footballPool);
}
