package com.guna.expensight.repository;

import com.guna.expensight.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser_Id(Long userId);
    Page<Expense> findByUser_Id(Long userId, Pageable pageable);
    List<Expense> findByCategory_Id(Long categoryId);
    Page<Expense> findByCategory_Id(Long categoryId, Pageable pageable);
    boolean existsByCategoryId(Long categoryId);
    boolean existsByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    BigDecimal getTotalAmount();
}
