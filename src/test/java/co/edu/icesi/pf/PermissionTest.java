package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.PermissionRepository;
import co.edu.icesi.pf.domain.model.records.Permission;
import co.edu.icesi.pf.domain.usecase.permission.CreatePermissionUseCase;
import co.edu.icesi.pf.domain.usecase.permission.DeletePermissionUseCase;
import co.edu.icesi.pf.domain.usecase.permission.GetPermissionUseCase;
import co.edu.icesi.pf.domain.usecase.permission.UpdatePermissionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermissionTest {

    @Mock
    private PermissionRepository permissionRepository;

    private CreatePermissionUseCase createPermissionUseCase;
    private DeletePermissionUseCase deletePermissionUseCase;
    private UpdatePermissionUseCase updatePermissionUseCase;
    private GetPermissionUseCase getPermissionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createPermissionUseCase = new CreatePermissionUseCase(permissionRepository);
        deletePermissionUseCase = new DeletePermissionUseCase(permissionRepository);
        updatePermissionUseCase = new UpdatePermissionUseCase(permissionRepository);
        getPermissionUseCase = new GetPermissionUseCase(permissionRepository);
    }

    @Test
    void testCreatePermission_Successful() {
        // Arrange
        String name = "EDIT_USER";
        Date now = new Date();

        Permission expectedPermission = new Permission(
                null, name, now, now, "ACTIVE"
        );

        when(permissionRepository.existsByName(name)).thenReturn(false);
        when(permissionRepository.save(any(Permission.class))).thenReturn(expectedPermission);

        // Act
        Permission createdPermission = createPermissionUseCase.execute(name);

        // Assert
        assertNotNull(createdPermission);
        assertEquals(name, createdPermission.name());
        assertEquals("ACTIVE", createdPermission.state());
        verify(permissionRepository).existsByName(name);
        verify(permissionRepository).save(any(Permission.class));
    }

    @Test
    void testCreatePermission_DuplicateName_ThrowsException() {
        // Arrange
        String name = "EDIT_USER";

        when(permissionRepository.existsByName(name)).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createPermissionUseCase.execute(name);
        });

        assertEquals("El permiso con el nombre EDIT_USER ya existe", exception.getMessage());
        verify(permissionRepository).existsByName(name);
        verify(permissionRepository, never()).save(any(Permission.class));
    }

    @Test
    void testDeletePermission_Successful() {
        // Arrange
        String permissionId = "perm123";

        when(permissionRepository.existsById(permissionId)).thenReturn(true);

        Permission deletedPermission = new Permission(
                permissionId,
                "MANAGE_USERS",
                new Date(),
                new Date(),
                "DELETED"
        );
        when(permissionRepository.softDelete(permissionId)).thenReturn(deletedPermission);

        // Act
        ResponseEntity<Permission> response = deletePermissionUseCase.execute(permissionId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("DELETED", response.getBody().state());
        assertEquals(permissionId, response.getBody().uuid());
    }

    @Test
    void testDeletePermission_NonExistent_ThrowsException() {
        // Arrange
        String permissionId = "nonexistent123";

        when(permissionRepository.existsById(permissionId)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deletePermissionUseCase.execute(permissionId);
        });

        assertEquals("No existe el permiso con el id: " + permissionId, exception.getMessage());
    }

    @Test
    void testUpdatePermission_Successful() {
        // Arrange
        String id = "perm123";
        String newName = "EDIT_USERS";
        String newState = "ACTIVE";

        Date createdAt = new Date(System.currentTimeMillis() - 10000);
        Date updatedAt = new Date();

        // Permiso actual en la "BD"
        Permission existingPermission = new Permission(
                id,
                "OLD_NAME",
                createdAt,
                createdAt,
                "INACTIVE"
        );

        // Permiso esperado después del update
        Permission updatedPermission = new Permission(
                id,
                newName,
                createdAt,
                updatedAt,
                newState
        );

        // Mocks
        when(permissionRepository.existsById(id)).thenReturn(true);
        when(permissionRepository.getPermissionById(id)).thenReturn(existingPermission);
        when(permissionRepository.update(any(Permission.class))).thenReturn(updatedPermission);

        // Act
        Permission result = updatePermissionUseCase.execute(id, newName, newState);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.uuid());
        assertEquals(newName, result.name());
        assertEquals(newState, result.state());
        assertEquals(createdAt, result.created_at());
        assertTrue(result.updated_at().after(createdAt)); // updatedAt should be newer
        verify(permissionRepository, times(1)).update(any(Permission.class));
    }

    @Test
    void testUpdatePermission_NonExistent_ThrowsException() {
        // Arrange
        String id = "nonexistent-permission";
        String newName = "EDIT_USERS";
        String newState = "ACTIVE";

        when(permissionRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updatePermissionUseCase.execute(id, newName, newState);
        });

        assertEquals("No existe ningun permiso con el id: " + id, exception.getMessage());
    }

    @Test
    void testGetAllPermissions_Successful() {
        // Arrange
        List<Permission> mockPermissions = List.of(
                new Permission("perm1", "READ_USERS", new Date(), new Date(), "ACTIVE"),
                new Permission("perm2", "WRITE_USERS", new Date(), new Date(), "INACTIVE")
        );

        when(permissionRepository.getAll()).thenReturn(mockPermissions);

        // Act
        List<Permission> result = getPermissionUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("perm1", result.get(0).uuid());
        assertEquals("perm2", result.get(1).uuid());
        verify(permissionRepository, times(1)).getAll();
    }

    @Test
    void testGetAllPermissions_EmptyList() {
        // Arrange
        when(permissionRepository.getAll()).thenReturn(Collections.emptyList());

        // Act
        List<Permission> result = getPermissionUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(permissionRepository, times(1)).getAll();
    }


}
