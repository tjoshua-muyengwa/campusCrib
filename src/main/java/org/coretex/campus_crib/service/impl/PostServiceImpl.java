package org.coretex.campus_crib.service.impl;

import lombok.RequiredArgsConstructor;
import org.coretex.campus_crib.Exceptions.LoginException;
import org.coretex.campus_crib.dto.PostDto;
import org.coretex.campus_crib.entities.Post;
import org.coretex.campus_crib.entities.Session;
import org.coretex.campus_crib.entities.User;
import org.coretex.campus_crib.repository.PostRepository;
import org.coretex.campus_crib.repository.SessionRepository;
import org.coretex.campus_crib.repository.UserRepository;
import org.coretex.campus_crib.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;


    @Override
    public Post createPost(PostDto postDto, String username) throws LoginException {
        Optional<Session> sessionOptional = sessionRepository.findByUsername(username);
        if (sessionOptional.isEmpty()) {
            throw new LoginException("Login first");
        }

        Session session = sessionOptional.get();
        Optional<User> userOptional = userRepository.findByEmail(session.getEmail());
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUsername(session.getUsername());
        post.setLocation(postDto.getLocation());
        post.setPrice(postDto.getPrice());
        post.setCaption(postDto.getCaption());
        post.setPictureUrl(postDto.getPictureUrl());
        post.setOwner(user);
        post.setCreatedDate(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, String username) throws LoginException {

        Optional<Session> sessionOptional = sessionRepository.findByUsername(username);
        if (sessionOptional.isEmpty()) {
            throw new LoginException("Login first");
        }

        Session session = sessionOptional.get();
        Optional<User> userOptional = userRepository.findByEmail(session.getEmail());
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.orElseThrow(() -> new RuntimeException("Post not found"));


        if (!post.getOwner().equals(user)) {
            throw new RuntimeException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }

    public List<Post> myPosts(String username) {
       List<Post> posts  = postRepository.findByUsername(username);

       if (posts.isEmpty()) {
           throw new RuntimeException("Post not found");
       }
       return posts;
    }

}
