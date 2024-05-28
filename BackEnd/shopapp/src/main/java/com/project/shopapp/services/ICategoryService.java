package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
  Category createCategory(CategoryDTO categoryDTO);
  Category getCategoryById(Long id);
  List<Category> getAllCategories();
  Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
  void deleteCategory(Long id);

}
