package org.coretex.campus_crib.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique =  true)
    private Long userId;

    private String uuid;

    private String email;
    private String name;
    private String username;
    private Role role;
    private LocalDateTime localDateTime;


    public Session(Long userId, String uuid, String email, String name, Role role, LocalDateTime now) {
        super();
        this.userId = userId;
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.role = role;
        this.localDateTime = now;

    }

    public Session() {

    }
}