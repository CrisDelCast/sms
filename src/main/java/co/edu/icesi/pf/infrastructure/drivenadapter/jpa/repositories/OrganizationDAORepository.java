package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;

import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.OrganizationDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrganizationDAORepository extends JpaRepository<OrganizationDAO, String> {
    Optional<OrganizationDAO> findByNit(String nit);
    boolean existsByNit(String nit);

    // Other forms of search
    @Query("SELECT o FROM OrganizationDAO o WHERE o.nit = :nit")
    Optional<OrganizationDAO> findOrganizationByNit(@Param("nit") String nit);

    @Query("SELECT COUNT(o) > 0 FROM OrganizationDAO o WHERE o.nit = :nit")
    boolean organizationExistsByNit(@Param("nit") String nit);
}