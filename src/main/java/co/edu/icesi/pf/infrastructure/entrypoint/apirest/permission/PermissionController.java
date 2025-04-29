package co.edu.icesi.pf.infrastructure.entrypoint.apirest.permission;

import co.edu.icesi.pf.domain.model.records.Permission;
import co.edu.icesi.pf.domain.usecase.permission.CreatePermissionUseCase;
import co.edu.icesi.pf.domain.usecase.permission.DeletePermissionUseCase;
import co.edu.icesi.pf.domain.usecase.permission.GetPermissionUseCase;
import co.edu.icesi.pf.domain.usecase.permission.UpdatePermissionUseCase;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.permission.dto.PermissionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/permission")
public class PermissionController {

    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final GetPermissionUseCase getPermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        return ResponseEntity.ok(getPermissionUseCase.execute());
    }

    @PostMapping("/create")
    public ResponseEntity<Permission> create(@RequestBody PermissionDTO request) {

        Permission permission = createPermissionUseCase.execute(
                request.getName()
        );
        return ResponseEntity.ok(permission);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Permission> update(@PathVariable String id, @RequestBody PermissionDTO request) {
        Permission permission = updatePermissionUseCase.execute(
                id,
                request.getName(),
                request.getState()
        );
        return ResponseEntity.ok(permission);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Permission> delete(@PathVariable String id) {
        return deletePermissionUseCase.execute(id);
    }
}
