package co.edu.icesi.pf.domain.usecase.user;

import co.edu.icesi.pf.domain.model.gateways.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserExistUseCase {
    private final UserRepository UserRepository;

    public boolean execute(String id) {
        return UserRepository.existsById(id);
    }
}