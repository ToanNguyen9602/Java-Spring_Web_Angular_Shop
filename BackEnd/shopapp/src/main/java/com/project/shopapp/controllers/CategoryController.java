package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.services.CategoryService;
import com.project.shopapp.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.project.shopapp.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor

public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping("")
  public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                          BindingResult result) {
    if (result.hasErrors()) {
      List<String> errorMessages = result.getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .toList();
      return ResponseEntity.badRequest().body(errorMessages);
    }
    categoryService.createCategory(categoryDTO);
    return ResponseEntity.ok("Create category successfully");
  }

    //hiển thị tất cả category
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(
          @RequestParam("page") int page,
          @RequestParam("limit") int limit
    ) {
      List<Category> categories = categoryService.getAllCategories();
      return ResponseEntity.ok(categories);
    }


  @PutMapping("/{id}")
  public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
    categoryService.updateCategory(id, categoryDTO);
    return ResponseEntity.ok("Update category successfully");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.ok("This is deleteCategory with id= " + id);
  }
}
