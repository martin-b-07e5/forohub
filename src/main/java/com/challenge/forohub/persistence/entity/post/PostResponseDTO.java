package com.challenge.forohub.persistence.entity.post;

import java.time.LocalDateTime;

public record PostResponseDTO(
    Long id,
    String title,
    String content,
    String category,
    String username, // Nombre del usuario que creó el post
    LocalDateTime createdAt
//    LocalDateTime updatedAt
) {
  public PostResponseDTO(Post post) {
    this(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCategory().name(), // Convertimos el enum a String
        post.getUser().getUsername(), // Obtenemos el nombre del usuario
        post.getCreatedAt()
//        post.getUpdatedAt()
    );
  }
}