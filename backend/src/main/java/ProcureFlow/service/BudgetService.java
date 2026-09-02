package ProcureFlow.service;

import ProcureFlow.dto.CreateBudgetRequest;
import ProcureFlow.entity.Budget;
import ProcureFlow.entity.Department;
import ProcureFlow.repository.BudgetRepository;
import ProcureFlow.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final DepartmentRepository departmentRepository;

    public BudgetService(
            BudgetRepository budgetRepository,
            DepartmentRepository departmentRepository
    ) {
        this.budgetRepository = budgetRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget createBudget(CreateBudgetRequest request) {

        Department department = departmentRepository.findById(
                request.getDepartmentId()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Department not found"
        ));

        if (budgetRepository.existsByDepartmentIdAndYear(
                request.getDepartmentId(),
                request.getYear()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Budget for this department and year already exists"
            );
        }

        Budget budget = new Budget(
                department,
                request.getYear(),
                request.getAmount()
        );

        return budgetRepository.save(budget);
    }
}