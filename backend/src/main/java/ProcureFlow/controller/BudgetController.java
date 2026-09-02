package ProcureFlow.controller;

import ProcureFlow.dto.CreateBudgetRequest;
import ProcureFlow.entity.Budget;
import ProcureFlow.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @PostMapping
    public Budget createBudget(
            @Valid @RequestBody CreateBudgetRequest request) {

        return budgetService.createBudget(request);
    }
}