package org.coretex.campus_crib.service;

import org.coretex.campus_crib.Exceptions.LoginException;
import org.coretex.campus_crib.dto.PostDto;
import org.coretex.campus_crib.entities.Post;

import java.util.List;

public interface PostService {
    Post createPost(PostDto post, String username) throws LoginException;
    public void deletePost(Long postId, String username) throws LoginException;
    public List<Post> myPosts(String username);

}
