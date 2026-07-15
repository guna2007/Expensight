package com.guna.expensight.service;

import com.guna.expensight.dto.ExpenseDTO;
import com.guna.expensight.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(ExpenseDTO expenseDTO);
    List<Expense> getAllExpenses();
    Page<Expense> getAllExpenses(Pageable pageable);
    Expense getExpenseById(Long id);
    Expense updateExpense(Long id, ExpenseDTO expenseDTO);
    void deleteExpense(Long id);
    List<Expense> getExpensesByUser(Long userId);
    Page<Expense> getExpensesByUser(Long userId, Pageable pageable);
    List<Expense> getExpensesByCategory(Long categoryId);
    Page<Expense> getExpensesByCategory(Long categoryId, Pageable pageable);
}
