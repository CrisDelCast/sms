package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.FootballPool;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FootballPoolRepository {

    FootballPool save(FootballPool footballPool);
    List<FootballPool> findAll();
    Optional<FootballPool> findByUuid(UUID id);
    FootballPool update(UUID uuid, FootballPool footballPool);
    FootballPool softDelete(UUID uuid);



}