package co.edu.icesi.pf.domain.usecase.role;

import co.edu.icesi.pf.domain.model.gateways.repositories.RoleRepository;
import co.edu.icesi.pf.domain.model.records.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetRoleByIdUseCase {

    private final RoleRepository roleRepository;

    public Role execute(String id){
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe el rol con el id: " + id);
        }

        return roleRepository.getRoleById(id);
    }
}
