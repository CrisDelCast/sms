package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;

import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TournamentPhaseDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TournamentPhaseDAORepository extends JpaRepository<TournamentPhaseDAO, UUID> {

    Optional<TournamentPhaseDAO> findByUuid(UUID uuid);
}
