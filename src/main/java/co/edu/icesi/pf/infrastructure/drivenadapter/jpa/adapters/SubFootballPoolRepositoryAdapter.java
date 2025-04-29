package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.SubFootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.SubFootballPool;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.SubFootballPoolDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.SubFootballPoolMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.SubFootballPoolDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SubFootballPoolRepositoryAdapter implements SubFootballPoolRepository {
    private final SubFootballPoolDAORepository repository;

    @Autowired
    @Qualifier("SubFootballPoolMapperImpl")
    private final SubFootballPoolMapper mapper;


    @Override
    public SubFootballPool save(SubFootballPool subFootballPool) {
        SubFootballPoolDAO entity = mapper.toEntity(subFootballPool);
        SubFootballPoolDAO savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<SubFootballPool> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<SubFootballPool> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public SubFootballPool update(String id, SubFootballPool subFootballPool) {
        // Search SubFootballPool using Id
        SubFootballPoolDAO existingOrg = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubFootballPool not found"));

        // Update the existing entity using mapper
        SubFootballPoolDAO updatedEntity = mapper.toEntity(subFootballPool);
        updatedEntity.setUuid(existingOrg.getUuid());
        SubFootballPoolDAO savedEntity = repository.save(updatedEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean delete(String id) {
        if (existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
