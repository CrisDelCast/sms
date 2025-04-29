package co.edu.icesi.pf;

import co.edu.icesi.pf.domain.model.gateways.repositories.SubFootballPoolRepository;
import co.edu.icesi.pf.domain.model.records.SubFootballPool;
import co.edu.icesi.pf.domain.usecase.subfootballpool.*;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers.SubFootballPoolMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubFootBallPoolTests {

    @Mock
    private SubFootballPoolRepository repository;

    private CreateSubFootballPoolUseCase createUseCase;
    private ListSubFootballPoolByIdUseCase listUseCaseById;
    private ListSubFootballPoolUseCase listUseCase;
    private SubFootballPoolExistsUseCase existsUseCase;
    private UpdateSubFootballPoolUseCase updateUseCase;
    private DeleteSubFootballPoolUseCase deleteUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUseCase = new CreateSubFootballPoolUseCase(repository);
        listUseCaseById = new ListSubFootballPoolByIdUseCase(repository);
        listUseCase = new ListSubFootballPoolUseCase(repository);
        existsUseCase = new SubFootballPoolExistsUseCase(repository);
        updateUseCase = new UpdateSubFootballPoolUseCase(repository);
        deleteUseCase = new DeleteSubFootballPoolUseCase(repository);
    }

    // Helper method
    private SubFootballPool sampleSubFootballPool() {
        return new SubFootballPool(
                "My SubPool",
                LocalDate.now(),
                LocalDate.now(),
                "http://example.com/pool",
                UUID.randomUUID().toString()
        );
    }

    // ------------------ CreateSubFootballPoolUseCase Tests ------------------

    @Test
    void createSubFootballPoolSuccessfully() {
        SubFootballPool pool = sampleSubFootballPool();
        when(repository.save(pool)).thenReturn(pool);

        SubFootballPool result = createUseCase.execute(
                pool.name(),
                pool.creationDate(),
                pool.lastUpdated(),
                pool.url(),
                pool.footballPoolUuid()
        );

        assertEquals(pool, result);
        verify(repository).save(pool);
    }

    @Test
    void createSubFootballPoolWithInvalidUrl() {
        SubFootballPool pool = new SubFootballPool(
                "Name",
                LocalDate.now(),
                LocalDate.now(),
                "invalid_url",
                UUID.randomUUID().toString()
        );
        when(repository.save(pool)).thenReturn(pool);

        SubFootballPool result = createUseCase.execute(
                pool.name(),
                pool.creationDate(),
                pool.lastUpdated(),
                pool.url(),
                pool.footballPoolUuid()
        );

        assertEquals(pool.url(), result.url());
        verify(repository).save(pool);
    }

    // ------------------ ListSubFootballPoolByIdUseCase Tests ------------------

    @Test
    void listSubFootballPoolByIdSuccessfully() {
        String id = UUID.randomUUID().toString();
        SubFootballPool pool = sampleSubFootballPool();
        when(repository.findById(id)).thenReturn(Optional.of(pool));

        Optional<SubFootballPool> result = Optional.ofNullable(listUseCaseById.execute(id));

        assertTrue(result.isPresent());
        assertEquals(pool, result.get());
    }

    @Test
    void listSubFootballPoolByIdNotFound() {
        String id = UUID.randomUUID().toString();
        when(repository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> listUseCaseById.execute(id)
        );

        assertEquals("SubFootballPool not found for id: " + id, exception.getMessage());
    }

    @Test
    void listSubFootballPoolByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> listUseCaseById.execute(null));
    }

    // ------------------ ListSubFootballPoolUseCase Tests ------------------

    @Test
    void listAllSubFootballPoolsSuccessfully() {
        List<SubFootballPool> pools = List.of(sampleSubFootballPool(), sampleSubFootballPool());
        when(repository.findAll()).thenReturn(pools);

        List<SubFootballPool> result = listUseCase.execute();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void listAllSubFootballPoolsEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<SubFootballPool> result = listUseCase.execute();

        assertTrue(result.isEmpty());
    }

    @Test
    void listAllSubFootballPoolsReturnsCorrectData() {
        SubFootballPool pool = sampleSubFootballPool();
        when(repository.findAll()).thenReturn(List.of(pool));

        List<SubFootballPool> result = listUseCase.execute();

        assertEquals(pool.name(), result.getFirst().name());
    }

    // ------------------ SubFootballPoolExistsUseCase Tests ------------------

    @Test
    void existsSubFootballPoolTrue() {
        String id = UUID.randomUUID().toString();
        when(repository.existsById(id)).thenReturn(true);

        boolean result = existsUseCase.execute(id);

        assertTrue(result);
    }

    @Test
    void existsSubFootballPoolFalse() {
        String id = UUID.randomUUID().toString();
        when(repository.existsById(id)).thenReturn(false);

        boolean result = existsUseCase.execute(id);

        assertFalse(result);
    }

    @Test
    void existsSubFootballPoolWithNullId() {
        assertFalse(existsUseCase.execute(null));
    }

    // ------------------ UpdateSubFootballPoolUseCase Tests ------------------

    @Test
    void updateSubFootballPoolSuccessfully() {
        String id = UUID.randomUUID().toString();
        SubFootballPool pool = sampleSubFootballPool();
        when(repository.update(id, pool)).thenReturn(pool);

        SubFootballPool result = updateUseCase.execute(id,
                pool.name(),
                pool.creationDate(),
                pool.lastUpdated(),
                pool.url(),
                pool.footballPoolUuid()
        );

        assertEquals(pool, result);
    }

    @Test
    void updateSubFootballPoolReturnsUpdatedValues() {
        String id = UUID.randomUUID().toString();
        SubFootballPool updatedPool = new SubFootballPool(
                "Updated Pool",
                LocalDate.now(),
                LocalDate.now(),
                "http://updated-url.com",
                UUID.randomUUID().toString()
        );
        when(repository.update(id, updatedPool)).thenReturn(updatedPool);

        SubFootballPool result = updateUseCase.execute(id,
                updatedPool.name(),
                updatedPool.creationDate(),
                updatedPool.lastUpdated(),
                updatedPool.url(),
                updatedPool.footballPoolUuid()
        );

        assertEquals("Updated Pool", result.name());
        assertEquals("http://updated-url.com", result.url());
    }

    // ------------------ DeleteSubFootballPoolUseCase Tests ------------------

    @Test
    void deleteSubFootballPoolSuccessfully() {
        String id = UUID.randomUUID().toString();
        when(repository.delete(id)).thenReturn(true);

        boolean result = deleteUseCase.execute(id);

        assertTrue(result);
        verify(repository).delete(id);
    }

    @Test
    void deleteSubFootballPoolWithNullId() {
        assertFalse(deleteUseCase.execute(null));
    }

    @Test
    void deleteSubFootballPoolFailure() {
        String id = UUID.randomUUID().toString();
        when(repository.delete(id)).thenReturn(false);

        boolean result = deleteUseCase.execute(id);

        assertFalse(result);
    }
}
