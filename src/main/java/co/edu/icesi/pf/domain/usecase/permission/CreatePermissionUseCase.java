package co.edu.icesi.pf.domain.usecase.permission;

import co.edu.icesi.pf.domain.model.gateways.repositories.PermissionRepository;
import co.edu.icesi.pf.domain.model.records.Permission;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CreatePermissionUseCase {

    private final PermissionRepository PermissionRepository;

    public Permission execute(
            String name
    ) {
        if (PermissionRepository.existsByName(name)) {
            throw new IllegalArgumentException("El permiso con el nombre " + name + " ya existe");
        }

        Date now = new Date();

        Permission permission = new Permission(
                null,
                name,
                now,
                now,
                "ACTIVE"
        );
        return PermissionRepository.save(permission);
    }

}
