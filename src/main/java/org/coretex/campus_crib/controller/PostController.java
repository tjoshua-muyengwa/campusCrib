package org.coretex.campus_crib.controller;

import lombok.RequiredArgsConstructor;
import org.coretex.campus_crib.Exceptions.LoginException;
import org.coretex.campus_crib.dto.PostDto;
import org.coretex.campus_crib.repository.PostRepository;
import org.coretex.campus_crib.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("/create/{username}")
    public ResponseEntity<?> create(@RequestBody PostDto post, @PathVariable String username) throws LoginException {
        return new ResponseEntity<>(postService.createPost(post,username), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestParam String username) {
        try {
            postService.deletePost(postId, username);
            return new ResponseEntity<>("Post deleted", HttpStatus.OK);
        } catch (LoginException e) {
            return ResponseEntity.status(401).body("Login required: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping("/allPosts")
    public ResponseEntity<?> getAllPosts() {
        System.out.println("fetching all posts");
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/myPosts/{username}")
    public ResponseEntity<?> getMyPosts(@PathVariable String username) {
        System.out.println("fetching my posts");
        return new ResponseEntity<>(postService.myPosts(username), HttpStatus.OK);
    }

}
