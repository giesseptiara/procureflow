package ProcureFlow.service;

import ProcureFlow.dto.ApprovalRequest;
import ProcureFlow.dto.CreatePurchaseRequest;
import ProcureFlow.entity.Budget;
import ProcureFlow.entity.Department;
import ProcureFlow.entity.PurchaseRequest;
import ProcureFlow.entity.PurchaseRequestStatus;
import ProcureFlow.repository.BudgetRepository;
import ProcureFlow.repository.DepartmentRepository;
import ProcureFlow.repository.PurchaseRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;
    private final DepartmentRepository departmentRepository;
    private final BudgetRepository budgetRepository;

    public PurchaseRequestService(
            PurchaseRequestRepository purchaseRequestRepository,
            DepartmentRepository departmentRepository,
            BudgetRepository budgetRepository
    ) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.departmentRepository = departmentRepository;
        this.budgetRepository = budgetRepository;
    }

    public List<PurchaseRequest> getAllPurchaseRequests() {
        return purchaseRequestRepository.findAll();
    }

    public PurchaseRequest createPurchaseRequest(
            CreatePurchaseRequest request
    ) {

        Department department = departmentRepository.findById(
                request.getDepartmentId()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Department not found"
        ));

        int currentYear = LocalDate.now().getYear();

        Budget budget = budgetRepository
                .findByDepartmentIdAndYear(
                        request.getDepartmentId(),
                        currentYear
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Budget not found for this department in " + currentYear
                ));

        BigDecimal remainingBudget = budget.getAmount()
                .subtract(budget.getUsedAmount());

        if (request.getEstimatedCost().compareTo(remainingBudget) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Estimated cost exceeds remaining budget"
            );
        }

        PurchaseRequest purchaseRequest = new PurchaseRequest(
                department,
                request.getItemName(),
                request.getQuantity(),
                request.getEstimatedCost(),
                request.getReason()
        );

        return purchaseRequestRepository.save(purchaseRequest);
    }

    public PurchaseRequest processApproval(
            Long id,
            ApprovalRequest request
    ) {

        // 1. Cari Purchase Request
        PurchaseRequest purchaseRequest =
                purchaseRequestRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Purchase request not found"
                        ));

        // 2. Pastikan masih PENDING
        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.PENDING) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Purchase request has already been processed"
            );
        }

        // 3. Jika APPROVED
        if (request.getApproved()) {

            int currentYear = LocalDate.now().getYear();

            Budget budget = budgetRepository
                    .findByDepartmentIdAndYear(
                            purchaseRequest.getDepartment().getId(),
                            currentYear
                    )
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Budget not found for this department in "
                                    + currentYear
                    ));

            BigDecimal remainingBudget = budget.getAmount()
                    .subtract(budget.getUsedAmount());

            // Pastikan budget masih cukup saat approval
            if (purchaseRequest.getEstimatedCost()
                    .compareTo(remainingBudget) > 0) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Insufficient remaining budget"
                );
            }

            // Tambahkan penggunaan budget
            BigDecimal newUsedAmount = budget.getUsedAmount()
                    .add(purchaseRequest.getEstimatedCost());

            budget.setUsedAmount(newUsedAmount);

            budgetRepository.save(budget);

            // Update purchase request
            purchaseRequest.setStatus(
                    PurchaseRequestStatus.APPROVED
            );

            purchaseRequest.setApprovedAt(
                    LocalDateTime.now()
            );

            purchaseRequest.setRejectionReason(null);

        } else {

            // 4. Jika REJECTED
            if (request.getRejectionReason() == null
                    || request.getRejectionReason().isBlank()) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Rejection reason is required"
                );
            }

            purchaseRequest.setStatus(
                    PurchaseRequestStatus.REJECTED
            );

            purchaseRequest.setRejectionReason(
                    request.getRejectionReason()
            );

            purchaseRequest.setApprovedAt(null);
        }

        return purchaseRequestRepository.save(purchaseRequest);
    }
}