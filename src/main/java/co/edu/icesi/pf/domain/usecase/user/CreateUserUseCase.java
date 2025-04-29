package co.edu.icesi.pf.domain.usecase.user;

import co.edu.icesi.pf.domain.model.gateways.repositories.UserRepository;
import co.edu.icesi.pf.domain.model.records.User;
import co.edu.icesi.pf.domain.service.PasswordEncryptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptionService passwordEncryptionService;

    public User execute(String id, String email, String phone, int age, boolean have_fa, 
                       String name, String nickname, String password, String photo, 
                       Date created_at, Date updated_at) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("Nickname already in use");
        }

        String encryptedPassword = passwordEncryptionService.encryptPassword(password);

        User user = new User(
                id,
                email,
                phone,
                age,
                have_fa,
                name,
                nickname,
                encryptedPassword,
                photo,
                created_at,
                updated_at,
                "ACTIVE"
        );
        return userRepository.save(user);
    }
}