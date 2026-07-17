package com.guna.expensight.service;

import com.guna.expensight.dto.CategoryDTO;
import com.guna.expensight.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    List<Category> getAllCategories();
    Page<Category> getAllCategories(Pageable pageable);
    Category getCategoryById(Long id);
    Category updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
