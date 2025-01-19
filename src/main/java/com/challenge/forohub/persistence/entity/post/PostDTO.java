package com.challenge.forohub.persistence.entity.post;

import jakarta.validation.constraints.NotNull;


public record PostDTO(
    @NotNull String title,
    @NotNull String content,
    @NotNull PostCategoriesEnum category // Aquí usamos el enum
) {
}