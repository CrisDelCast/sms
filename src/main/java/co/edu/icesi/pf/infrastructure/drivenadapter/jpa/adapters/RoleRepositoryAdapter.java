package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.RoleRepository;
import co.edu.icesi.pf.domain.model.records.Role;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.RoleDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.RoleMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.RoleDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Repository
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleDAORepository repository;

    @Autowired
    @Qualifier("roleMapperImpl")
    private final RoleMapper mapper;

    @Override
    public Role save(Role role) {
        RoleDAO roleDAO = mapper.toEntity(role);
        roleDAO.setCreated_at(new Date());
        roleDAO.setUpdated_at(new Date());
        RoleDAO saved = repository.save(roleDAO);
        return mapper.toDomain(saved);
    }

    @Override
    public Role update(Role role) {
        if (!repository.existsById(role.uuid())) {
            throw new IllegalArgumentException("Role with id " + role.uuid() + " does not exist.");
        }

        RoleDAO entity = mapper.toEntity(role);
        entity.setUpdated_at(new Date());
        RoleDAO updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Role getRoleById(String id) {
        RoleDAO dao = repository.findByUuid(id);
        if (dao == null) {
            throw new IllegalArgumentException("Role with id " + id + " does not exist.");
        }
        return mapper.toDomain(dao);
    }

    @Override
    public Role softDelete(String id) {
        RoleDAO dao = repository.findByUuid(id);
        if (dao == null) {
            throw new IllegalArgumentException("Role with id " + id + " does not exist.");
        }

        dao.setState("DELETED");
        dao.setUpdated_at(new Date());
        RoleDAO updated = repository.save(dao);
        return mapper.toDomain(updated);
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsRoleDAOByUuid(id);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsRoleDAOByName(name);
    }
}
