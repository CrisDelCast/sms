package co.edu.icesi.pf.infrastructure.entrypoint.apirest.subfootballpool;

import co.edu.icesi.pf.domain.model.records.SubFootballPool;
import co.edu.icesi.pf.domain.usecase.subfootballpool.*;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.subfootballpool.dto.SubFootballPoolDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/sub-football-pools")
public class SubFootballPoolController {

    private final CreateSubFootballPoolUseCase createUseCase;
    private final ListSubFootballPoolUseCase listUseCase;
    private final ListSubFootballPoolByIdUseCase listByIdUseCase;
    private final SubFootballPoolExistsUseCase existsUseCase;
    private final UpdateSubFootballPoolUseCase updateUseCase;
    private final DeleteSubFootballPoolUseCase deleteUseCase;

    @PostMapping("/create")
    public ResponseEntity<SubFootballPool> create(@RequestBody SubFootballPoolDTO dto) {
        SubFootballPool subFootballPool = createUseCase.execute(
                dto.getName(),
                dto.getCreationDate(),
                dto.getLastUpdated(),
                dto.getUrl(),
                dto.getFootballPoolUuid()
        );

        return ResponseEntity.ok(subFootballPool);
    }

    @GetMapping
    public ResponseEntity<List<SubFootballPool>> getAll() {
        List<SubFootballPool> list = listUseCase.execute();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubFootballPool> getById(@PathVariable("id") String id) {
        SubFootballPool subFootballPool = listByIdUseCase.execute(id);
        return ResponseEntity.ok(subFootballPool);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable("id") String id) {
        boolean exists = existsUseCase.execute(id);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubFootballPool> update(@PathVariable("id") String id, @RequestBody SubFootballPoolDTO dto) {
        SubFootballPool subFootballPool = updateUseCase.execute(
                id,
                dto.getName(),
                dto.getCreationDate(),
                dto.getLastUpdated(),
                dto.getUrl(),
                dto.getFootballPoolUuid()
        );

        return ResponseEntity.ok(subFootballPool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
