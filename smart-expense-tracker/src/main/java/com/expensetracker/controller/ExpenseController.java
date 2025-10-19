package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("expenses", service.getAllExpenses());
        model.addAttribute("total", service.getTotalAmount());
        return "index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "add";
    }

    @GetMapping("/view")
    public String viewAll(Model model) {
    model.addAttribute("expenses", service.getAllExpenses());
    model.addAttribute("total", service.getTotalAmount());
    return "view";
}

    @PostMapping("/add")
    public String addExpense(@ModelAttribute Expense expense) {
        // Set default date to today if not provided
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        service.addExpense(expense);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable int id) {
        service.deleteExpense(id);
        return "redirect:/";
    }

}
