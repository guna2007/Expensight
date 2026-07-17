package com.guna.expensight.config;

import com.guna.expensight.model.Category;
import com.guna.expensight.model.Expense;
import com.guna.expensight.model.User;
import com.guna.expensight.repository.CategoryRepository;
import com.guna.expensight.repository.ExpenseRepository;
import com.guna.expensight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User user1 = userRepository.save(User.builder()
                .name("Rahul Kumar").email("rahul.kumar@company.com")
                .password(passwordEncoder.encode("rahul123")).build());
        User user2 = userRepository.save(User.builder()
                .name("Priya Sharma").email("priya.sharma@company.com")
                .password(passwordEncoder.encode("priya123")).build());
        User user3 = userRepository.save(User.builder()
                .name("Arjun Mehta").email("arjun.mehta@company.com")
                .password(passwordEncoder.encode("arjun123")).build());

        Category food = categoryRepository.save(Category.builder().name("Food & Beverages").build());
        Category transport = categoryRepository.save(Category.builder().name("Transport").build());
        Category shopping = categoryRepository.save(Category.builder().name("Shopping").build());
        Category bills = categoryRepository.save(Category.builder().name("Bills & Utilities").build());
        Category health = categoryRepository.save(Category.builder().name("Health & Fitness").build());
        Category subscriptions = categoryRepository.save(Category.builder().name("Subscriptions").build());
        Category travel = categoryRepository.save(Category.builder().name("Travel").build());

        expenseRepository.save(Expense.builder().description("Breakfast at Starbucks").amount(new BigDecimal("320.00")).date(LocalDate.of(2025, 9, 1)).user(user1).category(food).build());
        expenseRepository.save(Expense.builder().description("Uber to office").amount(new BigDecimal("250.00")).date(LocalDate.of(2025, 9, 1)).user(user1).category(transport).build());
        expenseRepository.save(Expense.builder().description("Netflix subscription").amount(new BigDecimal("649.00")).date(LocalDate.of(2025, 9, 1)).user(user1).category(subscriptions).build());
        expenseRepository.save(Expense.builder().description("Lunch Zomato - Biryani").amount(new BigDecimal("400.00")).date(LocalDate.of(2025, 9, 2)).user(user1).category(food).build());
        expenseRepository.save(Expense.builder().description("Electricity bill").amount(new BigDecimal("1800.00")).date(LocalDate.of(2025, 9, 3)).user(user1).category(bills).build());

        expenseRepository.save(Expense.builder().description("Gym membership").amount(new BigDecimal("2000.00")).date(LocalDate.of(2025, 9, 1)).user(user2).category(health).build());
        expenseRepository.save(Expense.builder().description("Metro card recharge").amount(new BigDecimal("1000.00")).date(LocalDate.of(2025, 9, 2)).user(user2).category(transport).build());
        expenseRepository.save(Expense.builder().description("Swiggy dinner - Thai curry").amount(new BigDecimal("550.00")).date(LocalDate.of(2025, 9, 2)).user(user2).category(food).build());
        expenseRepository.save(Expense.builder().description("Amazon Prime subscription").amount(new BigDecimal("1499.00")).date(LocalDate.of(2025, 9, 3)).user(user2).category(subscriptions).build());
        expenseRepository.save(Expense.builder().description("Myntra - Formal shirt").amount(new BigDecimal("1800.00")).date(LocalDate.of(2025, 9, 3)).user(user2).category(shopping).build());

        expenseRepository.save(Expense.builder().description("Ola ride to airport").amount(new BigDecimal("800.00")).date(LocalDate.of(2025, 9, 1)).user(user3).category(transport).build());
        expenseRepository.save(Expense.builder().description("Flight ticket - Goa").amount(new BigDecimal("4500.00")).date(LocalDate.of(2025, 9, 1)).user(user3).category(travel).build());
        expenseRepository.save(Expense.builder().description("Dinner at beach shack").amount(new BigDecimal("1200.00")).date(LocalDate.of(2025, 9, 1)).user(user3).category(food).build());
        expenseRepository.save(Expense.builder().description("Hotel stay - Goa").amount(new BigDecimal("3500.00")).date(LocalDate.of(2025, 9, 2)).user(user3).category(travel).build());
        expenseRepository.save(Expense.builder().description("Spotify subscription").amount(new BigDecimal("119.00")).date(LocalDate.of(2025, 9, 3)).user(user3).category(subscriptions).build());
    }
}
