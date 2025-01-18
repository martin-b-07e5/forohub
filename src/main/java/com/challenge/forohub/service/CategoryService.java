package com.challenge.forohub.service;

import com.challenge.forohub.exception.NameAlreadyInUseException;
import com.challenge.forohub.persistence.entity.Category.Category;
import com.challenge.forohub.persistence.entity.Category.CategoryDTO;
import com.challenge.forohub.persistence.repository.ICategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class CategoryService {

  // Dependencies injection
  private final ICategoryRepository categoryRepository;

  public CategoryService(ICategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }


  /// CREATE -------------------------------------------------------------
  @Transactional
  public Category addCategory(CategoryDTO categoryDTO) {

//     Verify unique name
    if (categoryRepository.existsByName(categoryDTO.name())) {
      throw new NameAlreadyInUseException("Name IS ALREADY IN USE");
    }
    // Truncar la fecha a segundos antes de persistir
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    // Create a new Category entity from the DTO
    Category category = Category.builder()
        .name(categoryDTO.name())
        .description(categoryDTO.description())
//        .createdAt(LocalDateTime.now())
        .createdAt(now)
        .build();

    // Save the category to the database
    return categoryRepository.save(category);
  }


}
