package com.guna.expensight.service.impl;

import com.guna.expensight.dto.DashboardSummary;
import com.guna.expensight.repository.CategoryRepository;
import com.guna.expensight.repository.ExpenseRepository;
import com.guna.expensight.repository.UserRepository;
import com.guna.expensight.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    @Override
    public DashboardSummary getSummary() {
        return DashboardSummary.builder()
                .totalUsers(userRepository.count())
                .totalCategories(categoryRepository.count())
                .totalExpenses(expenseRepository.count())
                .totalAmount(expenseRepository.getTotalAmount())
                .build();
    }
}
