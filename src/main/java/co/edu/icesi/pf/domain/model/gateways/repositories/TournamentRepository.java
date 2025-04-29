package co.edu.icesi.pf.domain.model.gateways.repositories;

import co.edu.icesi.pf.domain.model.records.Tournament;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository {
    Tournament save(Tournament tournament);
    List<Tournament> findAll();
    List<Tournament> findAllByState(String state);
    Tournament update(Tournament tournament);
    void delete(String uuid);
}
