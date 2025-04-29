package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.Organization;

import java.util.Optional;

public interface OrganizationRepository {
    Organization save(Organization organization);
    Optional<Organization> findByNit(String nit);
    boolean existsByNit(String nit);
    Organization update(Organization organization);
    Organization softDelete(String nit);
}