package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.FootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.FootballPool;
import co.edu.icesi.pf.domain.model.records.Tournament;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.FootballPoolDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.FootballPoolMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.FootballPoolDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class FootballPoolRepositoryAdapter implements FootballPoolRepository {

    private final FootballPoolDAORepository repository;
    private final FootballPoolMapper mapper;

    @Override
    public FootballPool save(FootballPool footballPool) {

        FootballPoolDAO entity = mapper.toEntity(footballPool);
        FootballPoolDAO savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);

    }

    @Override
    public List<FootballPool> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<FootballPool> findByUuid(UUID uuid) {

        Optional<FootballPoolDAO> footballPoolDAO = repository.findByUuid(uuid);
        return footballPoolDAO.map(mapper::toDomain);
    }

    @Override
    public FootballPool update(UUID uuid, FootballPool footballPool) {
        FootballPoolDAO existing = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Football Pool not found"));

        existing.setName(footballPool.name());
        existing.setState(footballPool.state());
        existing.setTournamentUuid(footballPool.tournamentUuid());
        existing.setChampionsWinPoints(footballPool.championsWinPoints());
        existing.setSecondPlaceWinPoints(footballPool.secondPlaceWinPoints());
        existing.setThirdPlaceWinPoints(footballPool.thirdPlaceWinPoints());
        existing.setWinnerTeamWinPoints(footballPool.winnerTeamWinPoints());
        existing.setTotalYellowCardsWinPoints(footballPool.totalYellowCardsWinPoints());
        existing.setTotalRedCardsWinPoints(footballPool.totalRedCardsWinPoints());
        existing.setTotalLocalGoalsWinPoints(footballPool.totalLocalGoalsWinPoints());
        existing.setTotalVisitingGoalWinPoints(footballPool.totalVisitingGoalWinPoints());

        FootballPoolDAO saved = repository.save(existing);
        return mapper.toDomain(saved);
    }

    @Override
    public FootballPool softDelete(UUID uuid) {
        FootballPoolDAO entity = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("FootballPool not found"));

        entity.setState("D");

        FootballPoolDAO updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

}
