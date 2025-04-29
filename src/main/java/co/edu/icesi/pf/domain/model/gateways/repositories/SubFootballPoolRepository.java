package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.SubFootballPool;

import java.util.List;
import java.util.Optional;

public interface SubFootballPoolRepository {
    SubFootballPool save(SubFootballPool subFootballPool);
    Optional<SubFootballPool> findById(String id);
    List<SubFootballPool> findAll();
    boolean existsById(String id);
    SubFootballPool update(String id, SubFootballPool subFootballPool);
    boolean delete(String id);
}