package com.challenge.forohub.persistence.repository;

import com.challenge.forohub.persistence.entity.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
}
