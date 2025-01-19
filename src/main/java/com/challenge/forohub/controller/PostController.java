package com.challenge.forohub.controller;

import com.challenge.forohub.persistence.entity.post.Post;
import com.challenge.forohub.persistence.entity.post.PostDTO;
import com.challenge.forohub.persistence.entity.post.PostListadoDTO;
import com.challenge.forohub.service.PostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/posts")
public class PostController {

  private static final Logger logger = LoggerFactory.getLogger(PostController.class);

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

  /// READ
  @GetMapping
  // http://localhost:8080/posts
  // http://localhost:8080/posts?page=0&size=3&sort=id,desc
  public Page<PostListadoDTO> listarPaginado(
      @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

    // Print by console (log)
    logger.info("Página solicitada: {}", pageable.getPageNumber());
    logger.info("Tamaño de página: {}", pageable.getPageSize());
    logger.info("Ordenamiento: {}", pageable.getSort());

    return postService.listarPaginado(pageable);
  }


}