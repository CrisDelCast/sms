package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TournamentPhaseDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TournamentPhaseMapper {

    TournamentPhase toDomain(TournamentPhaseDAO dao);
    TournamentPhaseDAO toEntity(TournamentPhase tournament);
}
