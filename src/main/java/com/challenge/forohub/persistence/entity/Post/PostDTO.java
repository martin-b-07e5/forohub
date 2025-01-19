package com.challenge.forohub.persistence.entity.Post;

import jakarta.validation.constraints.NotNull;


public record PostDTO(
    @NotNull String title,
    @NotNull String content,
    @NotNull CategoriesEnum category // Aquí usamos el enum
) {
}