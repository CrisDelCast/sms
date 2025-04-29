package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.PrizeRepository;
import co.edu.icesi.pf.domain.model.records.Prize;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.PrizeDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.PrizeMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.PrizeDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class PrizeRepositoryAdapter implements PrizeRepository {

    private final PrizeDAORepository repository;
    private final PrizeMapper mapper;

    @Override
    public Prize save(Prize prize) {
        PrizeDAO entity = mapper.toEntity(prize);
        PrizeDAO savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Prize> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Prize> findByUuid(UUID uuid) {
        Optional<PrizeDAO> prizeDAO = repository.findByUuid(uuid);
        return prizeDAO.map(mapper::toDomain);
    }

    @Override
    public Prize update(UUID uuid, Prize prize) {
        PrizeDAO existing = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Prize not found"));

        existing.setName(prize.name());
        existing.setImage(prize.image());
        existing.setOrganizationUuid(prize.organizationUuid());
        existing.setState(prize.state());

        PrizeDAO saved = repository.save(existing);
        return mapper.toDomain(saved);
    }

    @Override
    public Prize softDelete(UUID uuid) {
        PrizeDAO entity = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Prize not found"));

        entity.setState("D");

        PrizeDAO updated = repository.save(entity);
        return mapper.toDomain(updated);
    }
}