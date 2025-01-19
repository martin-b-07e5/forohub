package com.challenge.forohub.persistence.entity.comment;

import jakarta.validation.constraints.NotNull;

public record CommentDTO(
    @NotNull String content,
    @NotNull Long postId,  // ID del post al que pertenece el comentario
    @NotNull Long userId  // ID del usuario que crea el comentario
) {
}
