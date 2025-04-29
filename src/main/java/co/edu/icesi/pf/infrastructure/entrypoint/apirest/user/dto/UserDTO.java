package co.edu.icesi.pf.infrastructure.entrypoint.apirest.user.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserDTO {
    private String id;
    private String email;
    private String phone;
    private int age;
    private boolean have_fa;
    private String name;
    private String nickname;
    private String password;
    private String photo;
    private Date created_at;
    private Date updated_at;
    private String role_uuid;
    private String otp;
    private String state;
}