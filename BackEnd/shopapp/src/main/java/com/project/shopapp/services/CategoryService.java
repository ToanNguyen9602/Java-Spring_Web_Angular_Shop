package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

  private final CategoryRespository categoryRespository;

  @Override
  public Category createCategory(CategoryDTO categoryDTO) {
    Category newCategory = Category.builder()
                            .name(categoryDTO.getName())
                            .build();
    return categoryRespository.save(newCategory);
  }

  @Override
  public Category getCategoryById(Long id) {
    return categoryRespository.findById(id).orElseThrow(() -> new RuntimeException("CATEGORY NOT FULL"));
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRespository.findAll();
  }

  @Override
  public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) {
    Category existingCategory = getCategoryById(categoryId);
    existingCategory.setName(categoryDTO.getName());
    categoryRespository.save(existingCategory);
    return existingCategory;
  }

  @Override
  public void deleteCategory(Long id) {
      //xoá cứng => xoá xong mất luôn
      categoryRespository.deleteById(id);
    }

}
