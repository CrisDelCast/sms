package co.edu.icesi.pf.domain.usecase.organization;

import co.edu.icesi.pf.domain.model.gateways.repositories.OrganizationRepository;
import co.edu.icesi.pf.domain.model.records.Organization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateOrganizationUseCase {
    private final OrganizationRepository organizationRepository;

    public Organization execute(String nit, String name, String email, String logo, String phone, String themes, String banner) {
        Organization organization = new Organization(
                nit,
                name,
                email,
                logo,
                phone,
                themes,
                banner,
                "ACTIVE" // initial state
        );
        return organizationRepository.save(organization);
    }
}
