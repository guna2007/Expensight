package com.guna.expensight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/")
	public String dashboard() {
		return "dashboard";
	}

	@GetMapping("/users-page")
	public String usersPage() {
		return "users";
	}

	@GetMapping("/categories-page")
	public String categoriesPage() {
		return "categories";
	}

	@GetMapping("/expenses-page")
	public String expensesPage() {
		return "expenses";
	}

	@GetMapping("/add-expense-page")
	public String addExpensePage() {
		return "add-expense";
	}
}