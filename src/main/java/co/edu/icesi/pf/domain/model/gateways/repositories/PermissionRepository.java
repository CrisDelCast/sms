package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.Permission;

import java.util.List;
import java.util.UUID;

public interface PermissionRepository {
    // Add methods like save, findById, etc.
    Permission save(Permission permission);
    Permission update(Permission permission);
    Permission getPermissionById(String id);
    Permission softDelete(String id);
    List<Permission> getAll();
    boolean existsByName(String name);
    boolean existsById(String id);
}
