package co.edu.icesi.pf.domain.usecase.user;

import co.edu.icesi.pf.domain.model.gateways.repositories.UserRepository;
import co.edu.icesi.pf.domain.model.records.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class EditUserUseCase {
    private final UserRepository UserRepository;

    public User execute(String id, String email, String phone, int age, boolean have_fa, String name, String nickname, String password, String photo, String state, Date created_at, Date updated_at) {
        // Verify User exist
        if (!UserRepository.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found");
        }

        // Create update object
        User updatedUser = new User(
                id,
                email,
                phone,
                age,
                have_fa,
                name,
                nickname,
                password,
                photo,
                created_at,
                updated_at,
                state
        );

        // update in repository
        return UserRepository.update(updatedUser);
    }
}