package com.challenge.forohub.security;

import com.challenge.forohub.persistence.entity.Usuario;
import com.challenge.forohub.persistence.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

  @Autowired
  IUsuarioRepository usuarioRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    if (usuarioRepository.count() == 0) {
      Usuario admin = new Usuario();
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("admin"));
      admin.setRoles(Set.of("ROLE_ADMIN"));

      usuarioRepository.save(admin);
      System.out.println("Usuario creado con éxito.");
    }
    if (usuarioRepository.count() == 1) {
      Usuario usuario = new Usuario();
      usuario.setUsername("usuario");
      usuario.setPassword(passwordEncoder.encode("q1w2e3"));
      usuario.setRoles(Set.of("ROLE_USER"));

      usuarioRepository.save(usuario);
      System.out.println("Usuario creado con éxito.");
    }


  }
}
