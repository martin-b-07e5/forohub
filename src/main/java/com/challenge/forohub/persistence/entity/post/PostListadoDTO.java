package com.challenge.forohub.persistence.entity.post;

import com.challenge.forohub.security.Usuario;

import java.time.LocalDateTime;

public record PostListadoDTO(
    Long id,
    String title,
    String content,
    Usuario user,
    PostCategoriesEnum category,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public PostListadoDTO(Post post) {
    this(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getUser(),
        post.getCategory(),
        post.getCreatedAt(),
        post.getUpdatedAt()
    );
  }
  // ver por qué no es necesario convertir a String post.getCategory().toString(),
}
