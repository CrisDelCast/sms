package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.UserRepository;
import co.edu.icesi.pf.domain.model.records.User;
import co.edu.icesi.pf.domain.usecase.user.CreateUserUseCase;
import co.edu.icesi.pf.domain.usecase.user.EditUserUseCase;
import co.edu.icesi.pf.domain.usecase.user.UserExistUseCase;
import co.edu.icesi.pf.domain.usecase.user.SoftDeleteUserUseCase;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.UserDAO;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.UserMapper;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.repositories.UserDAORepository;
import co.edu.icesi.pf.domain.service.PasswordEncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncryptionService passwordEncryptionService;

    private CreateUserUseCase createUserUseCase;
    private EditUserUseCase editUserUseCase;
    private UserExistUseCase userExistUseCase;
    private SoftDeleteUserUseCase softDeleteUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userMapper = Mappers.getMapper(UserMapper.class);

        createUserUseCase = new CreateUserUseCase(userRepository, passwordEncryptionService);
        editUserUseCase = new EditUserUseCase(userRepository);
        userExistUseCase = new UserExistUseCase(userRepository);
        softDeleteUserUseCase = new SoftDeleteUserUseCase(userRepository);
    }

    // Tests para Crear Usuario
    @Test
    void testCreateUser_Successful() {
        // Arrange
        String id = "user123";
        String email = "test@example.com";
        String phone = "+573001234567";
        int age = 25;
        boolean have_fa = true;
        String name = "Test User";
        String nickname = "testuser";
        String password = "password123";
        String photo = "photo.jpg";
        Date created_at = new Date();
        Date updated_at = new Date();

        User userToCreate = new User(
                id, email, phone, age, have_fa, name, nickname, password, photo, created_at, updated_at, "ACTIVE"
        );
        when(userRepository.save(any(User.class)))
                .thenReturn(userToCreate);

        // Act
        User createdUser = createUserUseCase.execute(
                id, email, phone, age, have_fa, name, nickname, password, photo, created_at, updated_at
        );

        // Assert
        assertNotNull(createdUser);
        assertEquals(id, createdUser.id());
        assertEquals(email, createdUser.email());
        assertEquals(name, createdUser.name());
        assertEquals("ACTIVE", createdUser.state());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_WithMinimalFields() {
        // Arrange
        String id = "user123";
        String email = "test@example.com";
        String phone = "+573001234567";
        int age = 25;
        boolean have_fa = false;
        String name = "Test User";
        String nickname = "testuser";
        String password = "password123";
        Date created_at = new Date();
        Date updated_at = new Date();

        User userToCreate = new User(
                id, email, phone, age, have_fa, name, nickname, password, null, created_at, updated_at, "ACTIVE"
        );

        when(userRepository.save(any(User.class)))
                .thenReturn(userToCreate);

        // Act
        User createdUser = createUserUseCase.execute(
                id, email, phone, age, have_fa, name, nickname, password, null, created_at, updated_at
        );

        // Assert
        assertNotNull(createdUser);
        assertEquals(id, createdUser.id());
        assertEquals(email, createdUser.email());
        assertEquals(name, createdUser.name());
        assertNull(createdUser.photo());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testEditUser_Successful() {
        // Arrange
        String id = "user123";
        String originalEmail = "original@example.com";
        String updatedEmail = "updated@example.com";
        String updatedPhone = "+573009876543";
        int updatedAge = 26;
        boolean updatedHaveFa = true;
        String updatedName = "Updated User";
        String updatedNickname = "updateduser";
        String updatedPassword = "newpassword123";
        String updatedPhoto = "updated-photo.jpg";
        String updatedState = "ACTIVE";

        Date created_at = new Date();
        Date updated_at = new Date();

        User updatedUser = new User(
                id, updatedEmail, updatedPhone, updatedAge, updatedHaveFa, updatedName,
                updatedNickname, updatedPassword, updatedPhoto, created_at, updated_at, updatedState
        );

        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.update(any(User.class)))
                .thenReturn(updatedUser);

        // Act
        User result = editUserUseCase.execute(
                id, updatedEmail, updatedPhone, updatedAge, updatedHaveFa, updatedName,
                updatedNickname, updatedPassword, updatedPhoto, updatedState, created_at, updated_at
        );

        // Assert
        assertNotNull(result);
        assertEquals(updatedEmail, result.email());
        assertEquals(updatedName, result.name());
        assertEquals(updatedNickname, result.nickname());
        verify(userRepository, times(1)).update(any(User.class));
    }


    @Test
    void testEditUser_NotFound() {
        // Arrange
        String id = "nonexistent";

        when(userRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            editUserUseCase.execute(
                    id, "email@example.com", "+573001234567", 25, true, "Name",
                    "nickname", "password", "photo.jpg", "ACTIVE",
                    new Date(), // created_at
                    new Date()  // updated_at
            );
        });
    }

    @Test
    void testUserExists_True() {
        // Arrange
        String id = "user123";
        when(userRepository.existsById(id)).thenReturn(true);

        // Act
        boolean exists = userExistUseCase.execute(id);

        // Assert
        assertTrue(exists);
        verify(userRepository, times(1)).existsById(id);
    }

    @Test
    void testUserExists_False() {
        // Arrange
        String id = "nonexistent";
        when(userRepository.existsById(id)).thenReturn(false);

        // Act
        boolean exists = userExistUseCase.execute(id);

        // Assert
        assertFalse(exists);
        verify(userRepository, times(1)).existsById(id);
    }

    @Test
    void testSoftDeleteUser_Successful() {
        // Arrange
        String id = "user123";
        Date created_at = new Date();
        Date updated_at = new Date();

        User originalUser = new User(
                id, "test@example.com", "+573001234567", 25, true, "Test User",
                "testuser", "password123", "photo.jpg", created_at, updated_at, "ACTIVE"
        );

        User deletedUser = new User(
                id, "test@example.com", "+573001234567", 25, true, "Test User",
                "testuser", "password123", "photo.jpg", created_at, updated_at, "INACTIVE"
        );

        when(userRepository.softDelete(id))
                .thenReturn(deletedUser);

        // Act
        User result = softDeleteUserUseCase.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals("INACTIVE", result.state());
        verify(userRepository, times(1)).softDelete(id);
    }


    @Test
    void testSoftDeleteUser_NonExistentUser() {
        // Arrange
        String id = "nonexistent";

        when(userRepository.softDelete(id))
                .thenThrow(new RuntimeException("User with ID " + id + " not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            softDeleteUserUseCase.execute(id);
        });
    }

    @Test
    void testCreateUser_WithDuplicateEmail() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(passwordEncryptionService.encryptPassword(anyString())).thenReturn("encrypted_password");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(
                "user123", email, "+573001234567", 25, true,
                "Test User", "testuser", "password123", "photo.jpg",
                new Date(), new Date()
            );
        });
    }

    @Test
    void testCreateUser_WithDuplicateNickname() {
        // Arrange
        String nickname = "testuser";
        when(userRepository.existsByNickname(nickname)).thenReturn(true);
        when(passwordEncryptionService.encryptPassword(anyString())).thenReturn("encrypted_password");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(
                "user123", "test@example.com", "+573001234567", 25, true,
                "Test User", nickname, "password123", "photo.jpg",
                new Date(), new Date()
            );
        });
    }

    @Test
    void testCreateUser_WithPasswordEncryption() {
        // Arrange
        String id = "user123";
        String email = "test@example.com";
        String password = "password123";
        String encryptedPassword = "encrypted_password";

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByNickname(anyString())).thenReturn(false);
        when(passwordEncryptionService.encryptPassword(password)).thenReturn(encryptedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            assertEquals(encryptedPassword, savedUser.password());
            return savedUser;
        });

        // Act
        User createdUser = createUserUseCase.execute(
            id, email, "+573001234567", 25, true,
            "Test User", "testuser", password, "photo.jpg",
            new Date(), new Date()
        );

        // Assert
        assertNotNull(createdUser);
        assertEquals(encryptedPassword, createdUser.password());
        verify(passwordEncryptionService).encryptPassword(password);
        verify(userRepository).save(any(User.class));
    }
}