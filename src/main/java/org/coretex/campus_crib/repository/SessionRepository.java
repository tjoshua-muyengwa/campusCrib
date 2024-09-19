package org.coretex.campus_crib.repository;


import org.coretex.campus_crib.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUuid(String uuid);
    Optional<Session> findByUserId (Long userId);
    Optional<Session> findByEmail (String email);
    Optional<Session> findByName (String name);
    Optional<Session> findByUsername (String userName);

}