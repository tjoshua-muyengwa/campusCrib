package org.coretex.campus_crib.dto;


import lombok.Data;
import lombok.Value;
import org.coretex.campus_crib.entities.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
@Data
public class SignUpRequest implements Serializable {
    String firstName;
    String lastName;
    String username_;
    String email;
    String password;
}