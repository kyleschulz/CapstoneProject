package org.aim.carpentry.expense;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/expenses")

public class ExpenseController {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping(path = "")
    public @ResponseBody Iterable<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Expense getExpense(@PathVariable (value = "id") Integer id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.get();
    }

    @PostMapping(path = "/")
    public @ResponseBody String createExpense(@RequestBody Expense expense) {
        expenseRepository.save(expense);
        return "Saved";
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody String updateExpense(@PathVariable(value = "id") Integer id, @RequestBody Expense expenseDetails) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        Expense expense = optionalExpense.get();

        expense.setTransactionDate(expenseDetails.getTransactionDate());
        expense.setTransactionDesc(expenseDetails.getTransactionDesc());
        expense.setLocationId(expenseDetails.getLocationId());
        expense.setTransactionAmount(expenseDetails.getTransactionAmount());
        expense.setExpenseId(expenseDetails.getExpenseId());
        expense.setProjectId(expenseDetails.getProjectId());
        expenseRepository.save(expense);

        return "Updated";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody String deleteExpense(@PathVariable(value = "id") Integer id) {
        expenseRepository.deleteById(id);
        return "Deleted";
    }
}