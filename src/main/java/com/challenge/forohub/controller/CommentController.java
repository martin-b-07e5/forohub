package com.challenge.forohub.controller;

import com.challenge.forohub.persistence.entity.comment.Comment;
import com.challenge.forohub.persistence.entity.comment.CommentDTO;
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
  public ResponseEntity<CommentDTO> createComment(@RequestBody @Valid CommentDTO commentDTO) {
    CommentDTO comment = commentService.createComment(commentDTO);
//    URI location = URI.create("/comments/" + comment.getId());
    URI location = URI.create("/comments/"); // No es necesario el ID en este caso
    return ResponseEntity.created(location).body(comment);
  }

}
