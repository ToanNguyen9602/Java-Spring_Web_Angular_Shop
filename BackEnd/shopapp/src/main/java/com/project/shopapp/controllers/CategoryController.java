package com.project.shopapp.controllers;

import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.responses.UpdateCategoryResponse;
import com.project.shopapp.services.CategoryService;
import com.project.shopapp.services.ICategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.project.shopapp.models.*;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor

public class CategoryController {
  private final CategoryService categoryService;
  private final MessageSource messageSource;
  private final LocaleResolver localeResolver;
  private final LocalizationUtils localizationUtils;

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
  public ResponseEntity<UpdateCategoryResponse> updateCategory(@PathVariable Long id, HttpServletRequest request,
                                                               @Valid @RequestBody CategoryDTO categoryDTO) {
    categoryService.updateCategory(id, categoryDTO);
    Locale locale = localeResolver.resolveLocale(request);
    return ResponseEntity.ok(UpdateCategoryResponse.builder()
            //tên controller. tên method
                    .message(messageSource.getMessage("category.update_category.update_successfully",
                            null,locale))
            .build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.ok("This is deleteCategory with id= " + id);
  }
}
