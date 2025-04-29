package co.edu.icesi.pf.infrastructure.entrypoint.apirest.organization;

import co.edu.icesi.pf.domain.model.records.Organization;
import co.edu.icesi.pf.domain.usecase.organization.CreateOrganizationUseCase;
import co.edu.icesi.pf.domain.usecase.organization.EditOrganizationUseCase;
import co.edu.icesi.pf.domain.usecase.organization.OrganizationExistsUseCase;
import co.edu.icesi.pf.domain.usecase.organization.SoftDeleteOrganizationUseCase;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.organization.dto.OrganizationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {

    private final CreateOrganizationUseCase createOrganizationUseCase;
    private final EditOrganizationUseCase editOrganizationUseCase;
    private final OrganizationExistsUseCase organizationExistsUseCase;
    private final SoftDeleteOrganizationUseCase softDeleteOrganizationUseCase;

    @PostMapping("/create")
    public ResponseEntity<Organization> createOrganization(@RequestBody OrganizationDTO request) {

        Organization organization = createOrganizationUseCase.execute(
                request.getNit(),
                request.getName(),
                request.getEmail(),
                request.getLogo(),
                request.getPhone(),
                request.getThemes(),
                request.getBanner()
        );
        return ResponseEntity.ok(organization);
    }

    @PutMapping("/edit/{nit}")
    public ResponseEntity<Organization> editOrganization(
            @PathVariable String nit,
            @RequestBody OrganizationDTO request
    ) {


        Organization organization = editOrganizationUseCase.execute(
                nit,
                request.getName(),
                request.getEmail(),
                request.getLogo(),
                request.getPhone(),
                request.getThemes(),
                request.getBanner(),
                request.getState()
        );
        return ResponseEntity.ok(organization);
    }

    @GetMapping("/exists/{nit}")
    public ResponseEntity<Boolean> organizationExists(@PathVariable String nit) {
        boolean exists = organizationExistsUseCase.execute(nit);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/delete/{nit}")
    public ResponseEntity<Organization> softDeleteOrganization(@PathVariable String nit) {

        Organization organization = softDeleteOrganizationUseCase.execute(nit);
        return ResponseEntity.ok(organization);
    }
}