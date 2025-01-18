package com.challenge.forohub.controller;

import com.challenge.forohub.persistence.entity.Category.Category;
import com.challenge.forohub.persistence.entity.Category.CategoryDTO;
import com.challenge.forohub.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  // dependencies injection
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  ///  CREATE ------------------------------------------------------------
  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {

    // Adds the new category's details to the database and gets the created entity with the generated ID.
    Category category = categoryService.addCategory(categoryDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(category.getId())  // Builds the URL with the generated ID
        .toUri();

    return ResponseEntity.created(location).body(category); // ResponseEntity.created(location) is the one that sets the 201 Created.

  }

}
