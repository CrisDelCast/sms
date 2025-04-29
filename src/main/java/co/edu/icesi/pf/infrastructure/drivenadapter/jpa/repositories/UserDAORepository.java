package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories;

import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDAORepository extends JpaRepository<UserDAO, String> {

    @Query("SELECT o FROM UserDAO o WHERE o.id = :id")
    Optional<UserDAO> findUserById(@Param("id") String id);

    @Query("SELECT COUNT(o) > 0 FROM UserDAO o WHERE o.id = :id")
    boolean userExistsById(@Param("id") String id);

    @Query("SELECT COUNT(o) > 0 FROM UserDAO o WHERE o.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(o) > 0 FROM UserDAO o WHERE o.nickname = :nickname")
    boolean existsByNickname(@Param("nickname") String nickname);
}