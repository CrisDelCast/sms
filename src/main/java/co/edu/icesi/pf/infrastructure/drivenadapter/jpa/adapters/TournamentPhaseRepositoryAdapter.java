package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentPhaseRepository;
import co.edu.icesi.pf.domain.model.records.TournamentPhase;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TournamentPhaseDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.TournamentPhaseMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.TournamentPhaseDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class TournamentPhaseRepositoryAdapter implements TournamentPhaseRepository {

    private final TournamentPhaseDAORepository tournamentPhaseDAORepository;
    private final TournamentPhaseMapper tournamentPhaseMapper;

    @Override
    public TournamentPhase save(TournamentPhase tournamentPhase) {
        TournamentPhaseDAO dao = tournamentPhaseMapper.toEntity(tournamentPhase);
        TournamentPhaseDAO savedDao = tournamentPhaseDAORepository.save(dao);
        return tournamentPhaseMapper.toDomain(savedDao);
    }

    @Override
    public List<TournamentPhase> findAll() {
        return tournamentPhaseDAORepository.findAll()
                .stream()
                .map(tournamentPhaseMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TournamentPhase> findByUuid(UUID id) {

        Optional<TournamentPhaseDAO> tournamentPhaseDAO = tournamentPhaseDAORepository.findByUuid(id);
        return tournamentPhaseDAO.map(tournamentPhaseMapper::toDomain);
    }

    @Override
    public TournamentPhase update(UUID uuid, TournamentPhase tournamentPhase) {

        TournamentPhaseDAO existingDao = tournamentPhaseDAORepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Tournament phase not found"));

        existingDao.setName(tournamentPhase.name());
        existingDao.setTournamentUuid(tournamentPhase.tournamentUuid());
        existingDao.setState(tournamentPhase.state());

        TournamentPhaseDAO updatedDao = tournamentPhaseDAORepository.save(existingDao);

        return tournamentPhaseMapper.toDomain(updatedDao);
    }

    @Override
    public TournamentPhase softDelete(UUID id) {

        TournamentPhaseDAO tournamentPhaseDAO = tournamentPhaseDAORepository.findByUuid(id)
                .orElseThrow(() -> new IllegalArgumentException("Tournament phase not found"));

        tournamentPhaseDAO.setState("D");

        TournamentPhaseDAO updated = tournamentPhaseDAORepository.save(tournamentPhaseDAO);
        return tournamentPhaseMapper.toDomain(updated);
    }
}
