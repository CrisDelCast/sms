package co.edu.icesi.pf.domain.usecase.role;

import co.edu.icesi.pf.domain.model.gateways.repositories.RoleRepository;
import co.edu.icesi.pf.domain.model.records.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetRoleUseCase {

    private final RoleRepository roleRepository;

    public List<Role> execute(){
        return roleRepository.getAll();
    }
}
