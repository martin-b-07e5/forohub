package com.challenge.forohub.service;

import com.challenge.forohub.persistence.entity.comment.Comment;
import com.challenge.forohub.persistence.entity.comment.CommentDTO;
import com.challenge.forohub.persistence.entity.comment.CommentResponseDTO;
import com.challenge.forohub.persistence.entity.post.Post;
import com.challenge.forohub.persistence.repository.ICommentRepository;
import com.challenge.forohub.persistence.repository.IPostRepository;
import com.challenge.forohub.persistence.repository.IUsuarioRepository;
import com.challenge.forohub.security.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

  private final ICommentRepository commentRepository;
  private final IPostRepository postRepository;
  private final IUsuarioRepository usuarioRepository;

  public CommentService(ICommentRepository commentRepository, IPostRepository postRepository, IUsuarioRepository usuarioRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.usuarioRepository = usuarioRepository;
  }

  /// CREATE a new comment
  @Transactional
  public CommentResponseDTO createComment(CommentResponseDTO commentResponseDTO) {
    // Obtener el post
    Post post = postRepository.findById(commentResponseDTO.postId())
        .orElseThrow(() -> new RuntimeException("Post no encontrado"));

    // Obtener el usuario
    Usuario user = usuarioRepository.findById(commentResponseDTO.userId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Crear el comentario
    Comment comment = Comment.builder()
        .content(commentResponseDTO.content())
        .post(post)
        .user(user)
        .createdAt(LocalDateTime.now())
        .build();

    // Guardar el comentario
    Comment savedComment = commentRepository.save(comment);

////    devolver el comentario
//    return new CommentResponseDTO(
//        savedComment.getId(),
//        savedComment.getContent(),
//        savedComment.getPost().getId(),
//        savedComment.getUser().getId()
//    );
    // Devolver el comentario usando el método de fábrica
    return CommentResponseDTO.fromEntity(savedComment);

  }

}