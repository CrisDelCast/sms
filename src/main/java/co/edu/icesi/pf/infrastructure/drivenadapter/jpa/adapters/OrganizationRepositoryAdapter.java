package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.OrganizationRepository;
import co.edu.icesi.pf.domain.model.records.Organization;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.OrganizationDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.OrganizationMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.OrganizationDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrganizationRepositoryAdapter implements OrganizationRepository {

    private final OrganizationDAORepository repository;
    private final OrganizationMapper mapper;

    @Override
    public Organization save(Organization organization) {
        OrganizationDAO entity = mapper.toEntity(organization);
        OrganizationDAO savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Organization> findByNit(String nit) {
        return repository.findByNit(nit).map(mapper::toDomain);
    }

    @Override
    public boolean existsByNit(String nit) {
        return repository.existsByNit(nit);
    }

    @Override
    public Organization update(Organization organization) {
        // Search organization using NIT
        OrganizationDAO existingOrg = repository.findByNit(organization.nit())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        // Update the existing entity using mapper
        OrganizationDAO updatedEntity = mapper.toEntity(organization);
        updatedEntity.setUuid(existingOrg.getUuid());

        // Save and return
        return mapper.toDomain(repository.save(updatedEntity));
    }

    @Override
    public Organization softDelete(String nit) {
        // Searching organization with NIT
        OrganizationDAO existingOrg = repository.findByNit(nit)
                .orElseThrow(() -> new RuntimeException("Organization with NIT " + nit + " not found"));

        // Change state to "INACTIVE"
        existingOrg.setState("INACTIVE");

        // Save and return
        return mapper.toDomain(repository.save(existingOrg));
    }
}