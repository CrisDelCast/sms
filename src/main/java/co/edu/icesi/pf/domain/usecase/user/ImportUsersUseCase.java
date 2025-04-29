package co.edu.icesi.pf.domain.usecase.user;

import co.edu.icesi.pf.domain.model.records.UserImportRequest;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.UserDAORepository;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.UserDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImportUsersUseCase {

    private final UserDAORepository userDAORepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ImportUsersUseCase(UserDAORepository userDAORepository, BCryptPasswordEncoder passwordEncoder) {
        this.userDAORepository = userDAORepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void importUsers(List<UserImportRequest> users) {
        for (UserImportRequest userRequest : users) {
            boolean exists = userDAORepository.existsByEmail(userRequest.email());
            if (!exists) {
                UserDAO userDAO = new UserDAO(
                        userRequest.id(),
                        userRequest.email(),
                        userRequest.phone(),
                        userRequest.age(),
                        userRequest.haveFa(),
                        userRequest.name(),
                        userRequest.nickname(),
                        passwordEncoder.encode(userRequest.password()),
                        userRequest.photo(),
                        new Date(),
                        new Date(),
                        userRequest.roleUuid(),
                        userRequest.otp(),
                        userRequest.state()
                );

                userDAORepository.save(userDAO);
            }
        }
    }
}
