package com.challenge.forohub.persistence.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.*;

public record CategoryDTO(
    String name,
    @NotNull(message = "NotNull") String description,
    @PastOrPresent LocalDateTime createdAt,
    @PastOrPresent LocalDateTime updatedAt
) implements Serializable {

}