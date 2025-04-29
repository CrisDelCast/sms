package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.adapters;

import co.edu.icesi.pf.domain.model.gateways.repositories.PermissionRepository;
import co.edu.icesi.pf.domain.model.records.Permission;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.PermissionDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.PermissionMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.PermissionDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class PermissionRepositoryAdapter implements PermissionRepository {

    private final PermissionDAORepository repository;

    @Autowired
    @Qualifier("permissionMapperImpl")
    private final PermissionMapper mapper;

    @Override
    public Permission save(Permission permission) {
        try {
            PermissionDAO entity = mapper.toEntity(permission);
            PermissionDAO saved = repository.save(entity);
            return mapper.toDomain(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el permiso en la base de datos", e);
        }
    }

    @Override
    public Permission update(Permission permission) {
        if (!repository.existsById(permission.uuid())) {
            throw new IllegalArgumentException("El permiso con el id " + permission.uuid() + " no existe");
        }
        PermissionDAO entity = mapper.toEntity(permission);
        PermissionDAO updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Permission getPermissionById(String id) {
        Optional<PermissionDAO> entity = Optional.ofNullable(repository.findByUuid(id));
        return entity.map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("El permiso con el id " + id + " no se encuentra"));
    }

    @Override
    public Permission softDelete(String id) {
        PermissionDAO permission = repository.findByUuid(id);

        if (permission == null) {
            throw new IllegalArgumentException("El permiso con el id " + id + " no existe");
        }

        permission.setState("DELETED");
        permission.setUpdated_at(new Date());

        PermissionDAO updated = repository.save(permission);
        return mapper.toDomain(updated);
    }


    @Override
    public List<Permission> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsPermissionDAOByName(name);
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsPermissionDAOByUuid(id);
    }
}
