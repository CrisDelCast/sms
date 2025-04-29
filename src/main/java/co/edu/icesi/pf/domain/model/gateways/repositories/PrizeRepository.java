package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.Prize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrizeRepository {

    Prize save(Prize prize);
    List<Prize> findAll();
    Optional<Prize> findByUuid(UUID id);
    Prize update(UUID uuid, Prize prize);
    Prize softDelete(UUID uuid);
}