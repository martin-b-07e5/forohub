package com.challenge.forohub.persistence.repository;

import com.challenge.forohub.persistence.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
}
