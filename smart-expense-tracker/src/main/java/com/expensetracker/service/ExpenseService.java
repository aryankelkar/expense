package com.expensetracker.service;

import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    public void addExpense(Expense expense) {
        repository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }


    public void deleteExpense(int id) {
        repository.deleteById(id);
    }

    public double getTotalAmount() {
        return repository.getTotalAmount();
    }
}
