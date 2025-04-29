package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.Role;

import java.util.List;

public interface RoleRepository {
    Role save(Role role);
    Role update(Role role);
    Role getRoleById(String id);
    Role softDelete(String id);
    List<Role> getAll();
    boolean existsById(String id);
    boolean existsByName(String name);
}
