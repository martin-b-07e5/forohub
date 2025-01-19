package com.challenge.forohub.persistence.entity.comment;

public record CommentResponseDTO(
    Long id,
    String content,
    Long postId,
    Long userId
) {
  // Método de fábrica
  public static CommentResponseDTO fromEntity(Comment comment) {
    return new CommentResponseDTO(
        comment.getId(),
        comment.getContent(),
        comment.getPost().getId(),
        comment.getUser().getId()
    );
  }

}
