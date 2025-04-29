package co.edu.icesi.pf.domain.usecase.permission;

import co.edu.icesi.pf.domain.model.gateways.repositories.PermissionRepository;
import co.edu.icesi.pf.domain.model.records.Permission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPermissionUseCase {

    private final PermissionRepository PermissionRepository;

    public List<Permission> execute() {
        return PermissionRepository.getAll();
    }

}
