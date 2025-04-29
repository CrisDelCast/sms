package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;

import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.PermissionDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionDAORepository extends JpaRepository<PermissionDAO, String> {

    @Query("SELECT COUNT(o) > 0 FROM PermissionDAO o WHERE o.uuid = :uuid")
    boolean existsPermissionDAOByUuid(@Param("uuid") String uuid);

    @Query("SELECT COUNT(o) > 0 FROM PermissionDAO o WHERE o.name = :name")
    boolean existsPermissionDAOByName(@Param("name") String name);

    PermissionDAO findByUuid(@Param("uuid") String uuid);
}
