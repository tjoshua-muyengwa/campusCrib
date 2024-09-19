package org.coretex.campus_crib.repository;

import org.coretex.campus_crib.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUsername(String username);
}
