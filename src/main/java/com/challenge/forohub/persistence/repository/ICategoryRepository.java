package com.challenge.forohub.persistence.repository;

import com.challenge.forohub.persistence.entity.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
//  Optional<Category> findByName(String name);

  boolean existsByName(String name);

}
