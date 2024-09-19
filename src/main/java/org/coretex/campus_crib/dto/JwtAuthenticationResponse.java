package org.coretex.campus_crib.dto;


import lombok.Data;
import org.coretex.campus_crib.entities.Role;
import org.coretex.campus_crib.entities.User;

/**
 * DTO for {@link User}
 */

@Data
public class JwtAuthenticationResponse{
    private Integer code;
    private String token;
    private Long userId;
    private String refreshToken;
    private String email;
    private String name;
    private String username_;
    private Role role;

}