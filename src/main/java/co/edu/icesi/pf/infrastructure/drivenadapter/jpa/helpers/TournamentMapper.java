package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.Tournament;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TournamentDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TournamentMapper {
    Tournament toDomain(TournamentDAO dao);
    TournamentDAO toEntity(Tournament tournament);

}