package com.challenge.forohub.persistence.entity.post;

import java.time.LocalDateTime;

public record PostListadoDTO(
    Long id,
    String title,
    String content,
    UsuarioDTO user,
    PostCategoriesEnum category,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
  public PostListadoDTO(Post post) {
    this(
        post.getId(),
        post.getTitle(),
        post.getContent(),
//        new UsuarioDTO(post.getUser()),  // Convertir el usuario al DTO simplificado
        UsuarioDTO.fromEntity(post.getUser()),  // Usar el método estático para simplificar
        post.getCategory(),
        post.getCreatedAt(),
        post.getUpdatedAt()
    );
  }
}
