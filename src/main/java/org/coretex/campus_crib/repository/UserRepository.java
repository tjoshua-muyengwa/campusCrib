package org.coretex.campus_crib.repository;


import org.coretex.campus_crib.entities.Role;
import org.coretex.campus_crib.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String username);
    User findByRole(Role role);
    boolean existsByEmail(String email);
}
