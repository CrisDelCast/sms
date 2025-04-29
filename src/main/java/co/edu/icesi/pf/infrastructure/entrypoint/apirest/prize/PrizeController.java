package co.edu.icesi.pf.infrastructure.entrypoint.apirest.prize;

import co.edu.icesi.pf.domain.model.records.Prize;
import co.edu.icesi.pf.domain.usecase.prize.*;
import co.edu.icesi.pf.infrastructure.entrypoint.apirest.prize.dto.PrizeDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/prizes")
public class PrizeController {

    private final CreatePrizeUseCase createUseCase;
    private final GetPrizesUseCase getPrizesUseCase;
    private final GetPrizeByIdUseCase getPrizeByIdUseCase;
    private final UpdatePrizeUseCase updatePrizeUseCase;
    private final DeletePrizeUseCase deletePrizeUseCase;

    @PostMapping("/create")
    public ResponseEntity<Prize> create(@RequestBody PrizeDTO request) {
        Prize prize = createUseCase.execute(
                request.getName(),
                request.getImage(),
                request.getOrganizationUuid(),
                request.getState()
        );

        return ResponseEntity.ok(prize);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Prize>> getAll() {
        List<Prize> prizes = getPrizesUseCase.execute();
        return ResponseEntity.ok(prizes);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<Prize> getById(@PathVariable UUID uuid) {
        Prize prize = getPrizeByIdUseCase.execute(uuid);
        return ResponseEntity.ok(prize);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Prize> update(@PathVariable UUID uuid, @RequestBody PrizeDTO request) {
        Prize updated = updatePrizeUseCase.execute(uuid, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Prize> delete(@PathVariable UUID uuid) {
        Prize deleted = deletePrizeUseCase.execute(uuid);
        return ResponseEntity.ok(deleted);
    }
}