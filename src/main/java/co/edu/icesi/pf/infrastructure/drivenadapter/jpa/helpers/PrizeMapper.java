package co.edu.icesi.pf.infrastructure.drivenadapter.jpa.helpers;

import co.edu.icesi.pf.domain.model.records.Prize;
import co.edu.icesi.pf.infrastructure.drivenadapter.jpa.data.PrizeDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrizeMapper {

    Prize toDomain(PrizeDAO dao);
    PrizeDAO toEntity(Prize prize);
}