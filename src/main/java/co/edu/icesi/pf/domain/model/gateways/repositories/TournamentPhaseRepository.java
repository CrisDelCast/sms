package co.edu.icesi.pf.domain.model.gateways.repositories;


import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TournamentPhaseRepository {

    TournamentPhase save(TournamentPhase tournamentPhase);
    List<TournamentPhase> findAll();
    Optional<TournamentPhase> findByUuid(UUID uuid);
    TournamentPhase update(UUID uuid, TournamentPhase tournamentPhase);
    TournamentPhase softDelete(UUID uuid);

}
