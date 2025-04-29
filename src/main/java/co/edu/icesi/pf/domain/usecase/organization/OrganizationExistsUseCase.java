package co.edu.icesi.pf.domain.usecase.organization;

import co.edu.icesi.pf.domain.model.gateways.repositories.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationExistsUseCase {
    private final OrganizationRepository organizationRepository;

    public boolean execute(String nit) {
        return organizationRepository.existsByNit(nit);
    }
}