package com.challenge.forohub.controller;

import com.challenge.forohub.persistence.entity.post.Post;
import com.challenge.forohub.persistence.entity.post.PostDTO;
import com.challenge.forohub.persistence.entity.post.PostListadoDTO;
import com.challenge.forohub.persistence.entity.post.PostUpdateDTO;
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

  /// CREATE
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

  @GetMapping("/{id}")  // Endpoint to get a post's details by ID
//  http://localhost:8080/posts/5
  public ResponseEntity<PostDTO> getMedicoById(@PathVariable Long id) {
    Post post = postService.getPostById(id);  // Fetch the post from the service
    if (post != null) {
      PostDTO postDTO = new PostDTO(post);  // Map the entity to DTO
      return ResponseEntity.ok(postDTO);  // Return the post's details in the response
    } else {
      return ResponseEntity.notFound().build();  // Return 404 if the doctor is not found
    }
  }


  /// UPDATE
  // PUT http://localhost:8080/posts
  @PutMapping
  public ResponseEntity<PostUpdateDTO> updatePost(@RequestBody @Valid PostUpdateDTO postUpdateDTO) {
    postService.updatePost(postUpdateDTO);  // Updates the post's details in the database
    Post updatedPost = postService.getPostById(postUpdateDTO.id()); // Fetches the updated post from the DB
    return ResponseEntity.ok(new PostUpdateDTO(updatedPost));  // Returns the updated post data in the response
  }

  // PUT http://localhost:8080/posts/{id}
  @PutMapping("/{id}")
  public ResponseEntity<PostUpdateDTO> updatePostByUrl(
      @PathVariable Long id,
      @RequestBody @Valid PostUpdateDTO postUpdateDTO) {

    Post updatedPost = postService.updatePostByUrl(id, postUpdateDTO); // Fetches the updated post from the DB
    return ResponseEntity.ok(new PostUpdateDTO(updatedPost));  // Returns the updated post data in the response
  }


  /// DELETE http://localhost:8080/posts/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);  // Deletes the post from the database
    return ResponseEntity.noContent().build();  // Returns 204 No Content status
  }

}