package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.Team;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository {

    Team save(Team team);
    List<Team> findAll();
    Optional<Team> findByUuid(UUID id);
    Team update(UUID uuid, Team team);
    Team softDelete(UUID uuid);
}