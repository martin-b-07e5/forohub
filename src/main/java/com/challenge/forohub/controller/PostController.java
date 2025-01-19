package com.challenge.forohub.controller;

import com.challenge.forohub.persistence.entity.Post.Post;
import com.challenge.forohub.persistence.entity.Post.PostDTO;
import com.challenge.forohub.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  /// CREATE a new post
  @PostMapping
  public ResponseEntity<Post> createPost(@RequestBody @Valid PostDTO postDTO) {

    Post post = postService.createPost(postDTO);
    URI location = URI.create("/posts" + post.getId());

    return ResponseEntity.created(location).body(post);
  }

}