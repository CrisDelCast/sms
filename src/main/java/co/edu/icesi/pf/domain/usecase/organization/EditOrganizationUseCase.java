package co.edu.icesi.pf.domain.usecase.organization;

import co.edu.icesi.pf.domain.model.gateways.repositories.OrganizationRepository;
import co.edu.icesi.pf.domain.model.records.Organization;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditOrganizationUseCase {
    private final OrganizationRepository organizationRepository;

    public Organization execute(String nit, String name, String email, String logo, String phone, String themes, String banner, String state) {
        // Verify organization exist
        if (!organizationRepository.existsByNit(nit)) {
            throw new RuntimeException("Organization with NIT " + nit + " not found");
        }

        // Create update object
        Organization updatedOrganization = new Organization(
                nit,
                name,
                email,
                logo,
                phone,
                themes,
                banner,
                state
        );

        // update in repository
        return organizationRepository.update(updatedOrganization);
    }
}