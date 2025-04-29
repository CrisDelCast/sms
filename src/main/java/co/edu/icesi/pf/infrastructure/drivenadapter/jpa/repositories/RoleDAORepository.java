package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;

import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.RoleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleDAORepository extends JpaRepository<RoleDAO, String> {

    @Query("SELECT COUNT(o) > 0 FROM RoleDAO o WHERE o.uuid = :uuid")
    boolean existsRoleDAOByUuid(@Param("uuid") String uuid);

    @Query("SELECT COUNT(o) > 0 FROM RoleDAO o WHERE o.name = :name")
    boolean existsRoleDAOByName(@Param("name") String name);

    RoleDAO findByUuid(@Param("uuid") String uuid);
}
