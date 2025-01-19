package com.challenge.forohub.controller;

import com.challenge.forohub.persistence.entity.comment.CommentResponseDTO;
import com.challenge.forohub.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/comments")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  /// CREATE a new comment
  @PostMapping
  public ResponseEntity<CommentResponseDTO> createComment(@RequestBody @Valid CommentResponseDTO commentResponseDTO) {
    CommentResponseDTO commentResponse = commentService.createComment(commentResponseDTO);
//    URI location = URI.create("/comments/" + commentResponse.id());
    URI location = URI.create("/comments/"); // No es necesario el ID en este caso
    return ResponseEntity.created(location).body(commentResponse);
  }

}