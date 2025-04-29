package co.edu.icesi.pf.domain.usecase.permission;

import co.edu.icesi.pf.domain.model.gateways.repositories.PermissionRepository;
import co.edu.icesi.pf.domain.model.records.Permission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UpdatePermissionUseCase {

    private final PermissionRepository PermissionRepository;

    public Permission execute(
            String uuid,
            String name,
            String state
    ) {
        if (!PermissionRepository.existsById(uuid)) {
            throw new IllegalArgumentException("No existe ningun permiso con el id: " + uuid);
        }

        Date now = new Date();

        Permission currentPermission = PermissionRepository.getPermissionById(uuid);

        Permission updatedPermission = new Permission(
                uuid, name, currentPermission.created_at(), now, state
        );
        return  PermissionRepository.update(updatedPermission);
    }

}
