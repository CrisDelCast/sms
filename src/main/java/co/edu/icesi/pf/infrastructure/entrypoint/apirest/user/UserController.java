package co.edu.icesi.pf.infrastructure.entrypoint.apirest.user;

import co.edu.icesi.pf.domain.model.records.User;
import co.edu.icesi.pf.domain.usecase.user.CreateUserUseCase;
import co.edu.icesi.pf.domain.usecase.user.EditUserUseCase;
import co.edu.icesi.pf.domain.usecase.user.UserExistUseCase;
import co.edu.icesi.pf.domain.usecase.user.SoftDeleteUserUseCase;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createuserUseCase;
    private final EditUserUseCase edituserUseCase;
    private final UserExistUseCase userExistsUseCase;
    private final SoftDeleteUserUseCase softDeleteuserUseCase;

    @PostMapping("/create")
    public ResponseEntity<User> createuser(@RequestBody UserDTO request) {

        User user = createuserUseCase.execute(
                request.getId(),
                request.getEmail(),
                request.getPhone(),
                request.getAge(),
                request.isHave_fa(),
                request.getName(),
                request.getNickname(),
                request.getPassword(),
                request.getPhoto(),
                request.getCreated_at(),
                request.getUpdated_at()
        );
        return ResponseEntity.ok(user);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> edituser(
            @PathVariable String id,
            @RequestBody UserDTO request
    ) {


        User user = edituserUseCase.execute(
                id,
                request.getEmail(),
                request.getPhone(),
                request.getAge(),
                request.isHave_fa(),
                request.getName(),
                request.getNickname(),
                request.getPassword(),
                request.getPhoto(),
                request.getState(),
                request.getCreated_at(),
                request.getUpdated_at()
        );
        return ResponseEntity.ok(user);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> userExists(@PathVariable String id) {
        boolean exists = userExistsUseCase.execute(id);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> softDeleteuser(@PathVariable String id) {

        User user = softDeleteuserUseCase.execute(id);
        return ResponseEntity.ok(user);
    }
}