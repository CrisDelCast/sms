package co.edu.icesi.pf.domain.usecase.user;

import co.edu.icesi.pf.domain.model.gateways.repositories.UserRepository;
import co.edu.icesi.pf.domain.model.records.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SoftDeleteUserUseCase {
    private final UserRepository UserRepository;

    public User execute(String id) {
        return UserRepository.softDelete(id);
    }
}