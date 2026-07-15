package com.guna.expensight.service.impl;

import com.guna.expensight.dto.CategoryDTO;
import com.guna.expensight.exception.ResourceNotFoundException;
import com.guna.expensight.model.Category;
import com.guna.expensight.repository.CategoryRepository;
import com.guna.expensight.repository.ExpenseRepository;
import com.guna.expensight.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = getCategoryById(id);
        category.setName(categoryDTO.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        if (expenseRepository.existsByCategoryId(id)) {
            throw new IllegalArgumentException("Cannot delete category: it is linked to existing expenses");
        }
        try {
            categoryRepository.delete(category);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Cannot delete category: it is still referenced by expenses");
        }
    }
}
