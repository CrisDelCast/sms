package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.UserRepository;
import co.edu.icesi.pf.domain.model.records.User;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.UserDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.UserMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.UserDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserDAORepository repository;

    @Autowired
    @Qualifier("userMapperImpl")
    private final UserMapper mapper;

    @Override
    public User save(User User) {
        UserDAO entity = mapper.toEntity(User);
        UserDAO savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findUserById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(String id) {
        return repository.userExistsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }      

    @Override
    public User update(User User) {
        // Search User using Id
        UserDAO existingOrg = repository.findUserById(User.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the existing entity using mapper
        UserDAO updatedEntity = mapper.toEntity(User);
        updatedEntity.setUuid(existingOrg.getUuid());

        // Save and return
        return mapper.toDomain(repository.save(updatedEntity));
    }

    @Override
    public User softDelete(String id) {
        // Searching User with id
        UserDAO existingOrg = repository.findUserById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));

        // Change state to "INACTIVE"
        existingOrg.setState("INACTIVE");

        // Save and return
        return mapper.toDomain(repository.save(existingOrg));
    }
}