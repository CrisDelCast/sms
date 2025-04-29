package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.TeamRepository;
import co.edu.icesi.pf.domain.model.records.Team;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TeamDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.TeamMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.TeamDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class TeamRepositoryAdapter implements TeamRepository {

    private final TeamDAORepository repository;
    private final TeamMapper mapper;

    @Override
    public Team save(Team team) {
        TeamDAO entity = mapper.toEntity(team);
        TeamDAO savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Team> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Team> findByUuid(UUID uuid) {
        Optional<TeamDAO> teamDAO = repository.findByUuid(uuid);
        return teamDAO.map(mapper::toDomain);
    }

    @Override
    public Team update(UUID uuid, Team team) {
        TeamDAO existing = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        existing.setName(team.name());
        existing.setNameAbbreviation(team.nameAbbreviation());
        existing.setTeamFlag(team.teamFlag());
        existing.setPoints(team.points());

        TeamDAO saved = repository.save(existing);
        return mapper.toDomain(saved);
    }

    @Override
    public Team softDelete(UUID uuid) {
        // Note: The team entity doesn't have a state field like FootballPool,
        // so this is a mock implementation. You might need to adjust this based on your requirements.
        TeamDAO entity = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // For a true soft delete, you would need to add a state field to TeamDAO
        // For now, we'll just return the entity
        return mapper.toDomain(entity);

        // If you want to implement actual soft delete by adding a state field:
        // entity.setState("D");
        // TeamDAO updated = repository.save(entity);
        // return mapper.toDomain(updated);
    }
}