package co.edu.icesi.pf.domain.usecase.role;

import co.edu.icesi.pf.domain.model.gateways.repositories.RoleRepository;
import co.edu.icesi.pf.domain.model.records.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UpdateRoleUseCase {

    private final RoleRepository roleRepository;

    public Role execute(
            String uuid,
            String name,
            String description,
            String state
    ) {
        if (!roleRepository.existsById(uuid)) {
            throw new IllegalArgumentException("No existe el rol con el id: " + uuid);
        }

        Date now  = new Date();

        Role currentRole = roleRepository.getRoleById(uuid);
        Role updatedRole = new Role(
                uuid, name, description, currentRole.created_at(), now, state
        );

        return roleRepository.update(updatedRole);
    }
}
