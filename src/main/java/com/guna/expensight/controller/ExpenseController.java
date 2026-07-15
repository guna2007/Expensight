package com.guna.expensight.controller;

import com.guna.expensight.dto.CategoryResponse;
import com.guna.expensight.dto.ExpenseDTO;
import com.guna.expensight.dto.ExpenseResponse;
import com.guna.expensight.dto.UserResponse;
import com.guna.expensight.model.Expense;
import com.guna.expensight.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toExpenseResponse(expenseService.createExpense(expenseDTO)));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponse>> getAllExpenses(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(expenseService.getAllExpenses(pageable).map(this::toExpenseResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(toExpenseResponse(expenseService.getExpenseById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseDTO expenseDTO
    ) {
        return ResponseEntity.ok(toExpenseResponse(expenseService.updateExpense(id, expenseDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ExpenseResponse>> getExpensesByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(expenseService.getExpensesByUser(userId, pageable).map(this::toExpenseResponse));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ExpenseResponse>> getExpensesByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(expenseService.getExpensesByCategory(categoryId, pageable).map(this::toExpenseResponse));
    }

    private UserResponse toUserResponse(com.guna.expensight.model.User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private CategoryResponse toCategoryResponse(com.guna.expensight.model.Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private ExpenseResponse toExpenseResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .user(toUserResponse(expense.getUser()))
                .category(toCategoryResponse(expense.getCategory()))
                .build();
    }
}
