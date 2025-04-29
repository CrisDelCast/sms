package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.UserRepository;
import co.edu.icesi.pf.domain.model.records.UserImportRequest;
import co.edu.icesi.pf.domain.usecase.user.ImportUsersUseCase;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.UserDAORepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImportUsersTest {

    @Mock
    private UserDAORepository userRepository;

    private ImportUsersUseCase importUsersUseCase;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        importUsersUseCase = new ImportUsersUseCase(userRepository, passwordEncoder);
    }

    @Test
    void testImportUsers_SuccessfulImport() {
        // Arrange
        List<UserImportRequest> usersToImport = List.of(
                new UserImportRequest("1", "user1@example.com", "3001112233", 25, true, "Juan Perez", "jperez", "123456", "photo.jpg", "ACTIVE", "", ""),
                new UserImportRequest("2", "user2@example.com", "3012223344", 30, false, "Maria Gomez", "mgomez", "abcdef", "photo2.jpg", "ACTIVE", "", "")
        );

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // Act
        assertDoesNotThrow(() -> importUsersUseCase.importUsers(usersToImport));

        // Assert
        verify(userRepository, times(2)).save(any());
    }

    @Test
    void testImportUsers_SkipDuplicates() {
        // Arrange
        List<UserImportRequest> usersToImport = List.of(
                new UserImportRequest("1", "user1@example.com", "3001112233", 25, true, "Juan Perez", "jperez", "123456", "photo.jpg", "ACTIVE", "", ""),
                new UserImportRequest("2", "user1@example.com", "3001112233", 25, true, "Juan Perez", "jperez", "123456", "photo.jpg", "ACTIVE", "", "") // Duplicado
        );

        when(userRepository.existsByEmail("user1@example.com"))
                .thenReturn(false)
                .thenReturn(true);

        // Act
        importUsersUseCase.importUsers(usersToImport);

        // Assert
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testImportUsers_EmptyExcel() {
        // Arrange
        List<UserImportRequest> emptyList = new ArrayList<>();

        // Act
        importUsersUseCase.importUsers(emptyList);

        // Assert
        verify(userRepository, never()).save(any());
    }
}
