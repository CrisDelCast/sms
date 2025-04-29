package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;

import co.edu.icesi.pf.domain.model.records.FootballPool;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.FootballPoolDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FootballPoolDAORepository extends JpaRepository<FootballPoolDAO, UUID> {

    Optional<FootballPoolDAO> findByUuid(UUID uuid);

}