package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.SubFootballPool;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.SubFootballPoolDAO;

public interface SubFootballPoolMapper {
    SubFootballPool toDomain(SubFootballPoolDAO dao);
    SubFootballPoolDAO toEntity(SubFootballPool subFootballPool);
}
