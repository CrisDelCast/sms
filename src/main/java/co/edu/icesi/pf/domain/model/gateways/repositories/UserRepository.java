package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    boolean existsById(String id);
    User update(User user);
    User softDelete(String id);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}