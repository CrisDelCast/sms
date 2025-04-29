package co.edu.icesi.pf.domain.model.records;

import java.util.Date;

public record User(
    String id,
    String email,
    String phone,
    int age,
    boolean have_fa,
    String name,
    String nickname,
    String password,
    String photo,
    Date created_at,
    Date updated_at,
    String state
){}