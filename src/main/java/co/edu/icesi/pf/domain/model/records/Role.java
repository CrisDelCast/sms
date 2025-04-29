package co.edu.icesi.pf.domain.model.records;

import java.util.Date;

public record Role(
      String uuid,
      String name,
      String description,
      Date created_at,
      Date updated_at,
      String state
) {
}
