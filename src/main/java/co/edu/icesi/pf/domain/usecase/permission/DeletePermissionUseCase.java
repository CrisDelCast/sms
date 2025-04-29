package co.edu.icesi.pf.domain.usecase.permission;

import co.edu.icesi.pf.domain.model.gateways.repositories.PermissionRepository;
import co.edu.icesi.pf.domain.model.records.Permission;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeletePermissionUseCase {

    private final PermissionRepository permissionRepository;

    public ResponseEntity<Permission> execute(String id) {
        if (!permissionRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe el permiso con el id: " + id);
        }

        return ResponseEntity.ok(permissionRepository.softDelete(id));
    }
}
