package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.TournamentRepository;
import co.edu.icesi.pf.domain.model.records.Tournament;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.TournamentDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.TournamentMapperImpl;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.TournamentDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class TournamentRepositoryAdapter implements TournamentRepository {

    private final TournamentDAORepository repository;
    private final TournamentMapperImpl mapper;

    @Override
    public Tournament save(Tournament tournament) {
        TournamentDAO entity = mapper.toEntity(tournament);
        TournamentDAO savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Tournament> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Tournament> findAllByState(String state) {
        List<TournamentDAO> tournamentDAOs = repository.findByState(state);
        return tournamentDAOs.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Tournament update(Tournament tournament) {
        // Primero buscamos el torneo existente
        TournamentDAO existingTournament = repository.findById(tournament.uuid())
            .orElseThrow(() -> new RuntimeException("Tournament not found"));

        // Actualizamos los campos usando el mapper
        TournamentDAO updatedEntity = mapper.toEntity(tournament);
        existingTournament.setName(updatedEntity.getName());
        existingTournament.setOrganizationUuid(updatedEntity.getOrganizationUuid());
        existingTournament.setStartingDate(updatedEntity.getStartingDate());
        existingTournament.setEndingDate(updatedEntity.getEndingDate());
        existingTournament.setState(updatedEntity.getState());

        // Guardamos los cambios
        return mapper.toDomain(repository.save(existingTournament));
    }

    @Override
    public void delete(String uuid) {
        TournamentDAO tournament = repository.findById(uuid)
            .orElseThrow(() -> new RuntimeException("Tournament not found"));
        
        tournament.setState("D"); // D para deleted
        repository.save(tournament);
    }
}
