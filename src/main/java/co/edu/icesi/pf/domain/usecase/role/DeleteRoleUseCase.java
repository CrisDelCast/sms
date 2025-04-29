package co.edu.icesi.pf.domain.usecase.role;

import co.edu.icesi.pf.domain.model.gateways.repositories.RoleRepository;
import co.edu.icesi.pf.domain.model.records.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
@AllArgsConstructor
public class DeleteRoleUseCase {

    private final RoleRepository roleRepository;

    public Role execute(
            String id
    ) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe el rol con el id: " + id);
        }

        return roleRepository.softDelete(id);
    }
}
