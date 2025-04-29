package co.edu.icesi.pf.domain.usecase.organization;

import co.edu.icesi.pf.domain.model.gateways.repositories.OrganizationRepository;
import co.edu.icesi.pf.domain.model.records.Organization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SoftDeleteOrganizationUseCase {
    private final OrganizationRepository organizationRepository;

    public Organization execute(String nit) {
        return organizationRepository.softDelete(nit);
    }
}