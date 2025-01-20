package com.challenge.forohub.service;

import com.challenge.forohub.exception.PostNotFoundException;
import com.challenge.forohub.persistence.entity.post.Post;
import com.challenge.forohub.persistence.entity.post.PostDTO;
import com.challenge.forohub.persistence.entity.post.PostListadoDTO;
import com.challenge.forohub.persistence.entity.post.PostUpdateDTO;
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


  /// CREATE
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
    Page<Post> postPage = postRepository.findAll(pageable);

    // Map the doctors page to a DTOs page
    return postPage.map(PostListadoDTO::new);
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(() -> new PostNotFoundException("Post with ID not found: " + id));
  }


  /// UPDATE
  @Transactional
  public void updatePost(PostUpdateDTO postUpdateDTO) {

    // Get the post from the DB
    Post post = postRepository.findById(postUpdateDTO.id())
        .orElseThrow(() -> new PostNotFoundException("Post with ID not found: " + postUpdateDTO.id()));

    // Update the post's fields
    if (postUpdateDTO.title() != null) {
      post.setTitle(postUpdateDTO.title());
    }
    if (postUpdateDTO.content() != null) {
      post.setContent(postUpdateDTO.content());
    }
    if (postUpdateDTO.category() != null) {
      post.setCategory(postUpdateDTO.category());
    }
    post.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

    // It is not necessary to call save() as the transaction will automatically save the changes.
//    postRepository.save(post);
  }

  @Transactional
  public Post updatePostByUrl(Long id, PostUpdateDTO postUpdateDTO) {

    // Get the post from the DB
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

    // Update the post's fields
    if (postUpdateDTO.title() != null) {
      post.setTitle(postUpdateDTO.title());
    }
    if (postUpdateDTO.content() != null) {
      post.setContent(postUpdateDTO.content());
    }
    if (postUpdateDTO.category() != null) {
      post.setCategory(postUpdateDTO.category());
    }
    post.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)); // Actualizar la fecha de modificación

    return postRepository.save(post);
  }


  /// DELETE
  @Transactional
  public void deletePost(Long id) {
    if (!postRepository.existsById(id)) {
      throw new PostNotFoundException("Post not found with ID: " + id);
    }
    postRepository.deleteById(id);
  }

}