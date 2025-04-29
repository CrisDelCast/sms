package co.edu.icesi.pf.domain.usecase.role;

import co.edu.icesi.pf.domain.model.gateways.repositories.RoleRepository;
import co.edu.icesi.pf.domain.model.records.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;

    public Role execute(
            String name,
            String description
    ) {

        if(name == null || description == null) {
            throw new IllegalArgumentException("El nombre del rol o descripcion no pueden estar vacios");
        }

        if (roleRepository.existsByName(name)) {
            throw new IllegalArgumentException("El rol con el nombre " + name + " ya existe");
        }

        Date now = new Date();

        Role role = new Role(
                null,
                name,
                description,
                now,
                now,
                "ACTIVE"
        );
        return roleRepository.save(role);
    }
}
