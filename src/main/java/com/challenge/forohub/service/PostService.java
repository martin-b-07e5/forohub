package com.challenge.forohub.service;

import com.challenge.forohub.persistence.entity.post.Post;
import com.challenge.forohub.persistence.entity.post.PostDTO;
import com.challenge.forohub.persistence.entity.post.PostListadoDTO;
import com.challenge.forohub.persistence.repository.IPostRepository;
import com.challenge.forohub.persistence.repository.IUsuarioRepository;
import com.challenge.forohub.security.Usuario;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class PostService {

  private final IPostRepository postRepository;
  private final IUsuarioRepository usuarioRepository;

  public PostService(IPostRepository postRepository, IUsuarioRepository usuarioRepository) {
    this.postRepository = postRepository;
    this.usuarioRepository = usuarioRepository;
  }


  /// CREATE a new post
  @Transactional
  public Post createPost(@Valid PostDTO postDTO) {
    Post post = Post.builder()
        .title(postDTO.title())
        .content(postDTO.content())
        .category(postDTO.category())  // Asignamos la categoría
        .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
        .updatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
        .build();

    // Obtener el usuario autenticado
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Usuario usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    // Asignar el usuario al post
    /* Aquí asignas el objeto `usuario` al campo `user` de la entidad `Post`.
       Este objeto contiene toda la información del usuario, incluyendo su ID. */
    post.setUser(usuario);

    // Guardar y devolver el post
    return postRepository.save(post);
  }


  /// READ
  public Page<PostListadoDTO> listarPaginado(Pageable pageable) {
    // Fetch the page of post from the repository. findAll(pageable) returns a page of entities (Post)
    Page<Post> medicosPage = postRepository.findAll(pageable);

    // Map the doctors page to a DTOs page
    return medicosPage.map(PostListadoDTO::new);
  }


}