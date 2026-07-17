package com.guna.expensight.service.impl;

import com.guna.expensight.dto.ExpenseDTO;
import com.guna.expensight.exception.ResourceNotFoundException;
import com.guna.expensight.model.Category;
import com.guna.expensight.model.Expense;
import com.guna.expensight.model.User;
import com.guna.expensight.repository.CategoryRepository;
import com.guna.expensight.repository.ExpenseRepository;
import com.guna.expensight.repository.UserRepository;
import com.guna.expensight.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Expense createExpense(ExpenseDTO expenseDTO) {
        User user = userRepository.findById(expenseDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + expenseDTO.getUserId()));

        Category category = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + expenseDTO.getCategoryId()));

        Expense expense = Expense.builder()
                .description(expenseDTO.getDescription())
                .amount(expenseDTO.getAmount())
                .date(expenseDTO.getDate())
                .user(user)
                .category(category)
                .build();

        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Page<Expense> getAllExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    }

    @Override
    public Expense updateExpense(Long id, ExpenseDTO expenseDTO) {
        Expense expense = getExpenseById(id);

        User user = userRepository.findById(expenseDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + expenseDTO.getUserId()));

        Category category = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + expenseDTO.getCategoryId()));

        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setDate(expenseDTO.getDate());
        expense.setUser(user);
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }

    @Override
    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUser_Id(userId);
    }

    @Override
    public Page<Expense> getExpensesByUser(Long userId, Pageable pageable) {
        return expenseRepository.findByUser_Id(userId, pageable);
    }

    @Override
    public List<Expense> getExpensesByCategory(Long categoryId) {
        return expenseRepository.findByCategory_Id(categoryId);
    }

    @Override
    public Page<Expense> getExpensesByCategory(Long categoryId, Pageable pageable) {
        return expenseRepository.findByCategory_Id(categoryId, pageable);
    }
}
