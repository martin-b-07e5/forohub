package com.challenge.forohub.security;

import com.challenge.forohub.persistence.entity.Usuario;
import com.challenge.forohub.persistence.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  private IUsuarioRepository usuarioRepository;

  public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
//    Usuario usuario = (Usuario) usuarioRepository.findByUsername(username);

    if (usuario.isEmpty()) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return usuario.orElse(null);
  }

}
