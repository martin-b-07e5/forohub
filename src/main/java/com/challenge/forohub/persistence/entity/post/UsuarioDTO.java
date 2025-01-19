package com.challenge.forohub.persistence.entity.post;

import com.challenge.forohub.security.Usuario;

public record UsuarioDTO(
    Long id,
    String username
) {
  public UsuarioDTO(Usuario usuario) {
    this(
        usuario.getId(),
        usuario.getUsername()
    );
  }

  // esto lo usamos en PostListadoDTO
  public static UsuarioDTO fromEntity(Usuario usuario) {
    return new UsuarioDTO(usuario.getId(), usuario.getUsername());
  }

}