package ProcureFlow.repository;

import ProcureFlow.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByDepartmentId(Long departmentId);

    boolean existsByDepartmentIdAndYear(
            Long departmentId,
            Integer year
    );

    Optional<Budget> findByDepartmentIdAndYear(
            Long departmentId,
            Integer year
    );
}