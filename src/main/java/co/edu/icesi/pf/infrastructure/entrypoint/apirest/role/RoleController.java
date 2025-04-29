package co.edu.icesi.pf.infrastructure.entrypoint.apirest.role;

import co.edu.icesi.pf.domain.model.records.Role;
import co.edu.icesi.pf.domain.usecase.role.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final GetRoleUseCase getRoleUseCase;
    private final GetRoleByIdUseCase getRoleByIdUseCase;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(getRoleUseCase.execute());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok().body(getRoleByIdUseCase.execute(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role request) {
        Role role = createRoleUseCase.execute(
                request.name(), request.description()
        );

        return ResponseEntity.ok().body(role);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable String id, @RequestBody Role request) {
        Role role = updateRoleUseCase.execute(
                id, request.name(), request.description(), request.state()
        );
        return ResponseEntity.ok().body(role);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable String id) {
        return ResponseEntity.ok().body(deleteRoleUseCase.execute(id));
    }
}
