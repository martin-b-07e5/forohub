package com.challenge.forohub.persistence.entity.post;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record PostUpdateDTO(
    Long id, // sin @NotNull para que funcione updatePostByUrl
    @NotNull String title,
    @NotNull String content,
    @NotNull PostCategoriesEnum category,
    LocalDateTime updatedAt  // sin @NotNull para que funcione updatePostByUrl
) {
  public PostUpdateDTO(Post post) {
    this(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCategory(),
        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
    );
  }
}