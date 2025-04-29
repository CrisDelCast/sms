package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.RoleRepository;
import co.edu.icesi.pf.domain.model.records.Role;
import co.edu.icesi.pf.domain.usecase.role.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleTest {

    @Mock
    private RoleRepository roleRepository;

    private CreateRoleUseCase createRoleUseCase;
    private UpdateRoleUseCase updateRoleUseCase;
    private DeleteRoleUseCase deleteRoleUseCase;
    private GetRoleUseCase getRoleUseCase;
    private GetRoleByIdUseCase getRoleByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createRoleUseCase = new CreateRoleUseCase(roleRepository);
        updateRoleUseCase = new UpdateRoleUseCase(roleRepository);
        deleteRoleUseCase = new DeleteRoleUseCase(roleRepository);
        getRoleUseCase = new GetRoleUseCase(roleRepository);
        getRoleByIdUseCase = new GetRoleByIdUseCase(roleRepository);
    }

    @Test
    void testCreateRole_Successful() {
        Role roleToCreate = new Role("role123", "Admin", "Admin role", new Date(), new Date(), "ACTIVE");

        when(roleRepository.existsByName("Admin")).thenReturn(false);
        when(roleRepository.save(any(Role.class))).thenReturn(roleToCreate);

        Role result = createRoleUseCase.execute("Admin", "Admin role");

        assertNotNull(result);
        assertEquals("Admin", result.name());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void testCreateRole_AlreadyExists_UnhappyPath() {
        when(roleRepository.existsByName("Admin")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            createRoleUseCase.execute("Admin", "Admin role");
        });

        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void testUpdateRole_Successful() {
        String id = "role123";
        Role existing = new Role(id, "Old Name", "Old Desc", new Date(), new Date(), "ACTIVE");
        Role updated = new Role(id, "New Name", "New Desc", existing.created_at(), new Date(), "ACTIVE");

        when(roleRepository.existsById(id)).thenReturn(true);
        when(roleRepository.getRoleById(id)).thenReturn(existing);
        when(roleRepository.update(any(Role.class))).thenReturn(updated);

        Role result = updateRoleUseCase.execute(id, "New Name", "New Desc", "ACTIVE");

        assertNotNull(result);
        assertEquals("New Name", result.name());
        assertEquals("New Desc", result.description());
    }

    @Test
    void testUpdateRole_NotFound_UnhappyPath() {
        when(roleRepository.existsById("invalid")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            updateRoleUseCase.execute("invalid", "Name", "Desc", "ACTIVE");
        });
    }

    @Test
    void testDeleteRole_Successful() {
        String id = "role123";
        Role deleted = new Role(id, "Admin", "Admin role", new Date(), new Date(), "DELETED");

        when(roleRepository.existsById(id)).thenReturn(true);
        when(roleRepository.softDelete(id)).thenReturn(deleted);

        Role result = deleteRoleUseCase.execute(id);

        assertEquals("DELETED", result.state());
        verify(roleRepository).softDelete(id);
    }

    @Test
    void testDeleteRole_NotFound_UnhappyPath() {
        when(roleRepository.existsById("missing")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            deleteRoleUseCase.execute("missing");
        });

        verify(roleRepository, never()).softDelete(anyString());
    }

    @Test
    void testGetAllRoles_Successful() {
        List<Role> roles = Arrays.asList(
                new Role("1", "Admin", "Admin role", new Date(), new Date(), "ACTIVE"),
                new Role("2", "User", "User role", new Date(), new Date(), "ACTIVE")
        );

        when(roleRepository.getAll()).thenReturn(roles);

        List<Role> result = getRoleUseCase.execute();

        assertEquals(2, result.size());
        assertEquals("Admin", result.get(0).name());
    }

    @Test
    void testGetAllRoles_EmptyList() {
        when(roleRepository.getAll()).thenReturn(List.of());

        List<Role> result = getRoleUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "La lista debería estar vacía");
    }

    @Test
    void testGetRoleById_Successful() {
        String id = "role123";
        Role role = new Role(id, "Admin", "Admin role", new Date(), new Date(), "ACTIVE");

        when(roleRepository.existsById(id)).thenReturn(true); // <- necesario
        when(roleRepository.getRoleById(id)).thenReturn(role);

        Role result = getRoleByIdUseCase.execute(id);

        assertNotNull(result);
        assertEquals("Admin", result.name());
        assertEquals("Admin role", result.description());
    }


    @Test
    void testGetRoleById_NotFound_UnhappyPath() {
        when(roleRepository.getRoleById("missing")).thenThrow(new IllegalArgumentException("Role not found"));

        assertThrows(IllegalArgumentException.class, () -> {
            getRoleByIdUseCase.execute("missing");
        });
    }
}
