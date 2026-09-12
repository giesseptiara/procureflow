package ProcureFlow.controller;

import ProcureFlow.dto.ApprovalRequest;
import ProcureFlow.dto.CreatePurchaseRequest;
import ProcureFlow.entity.PurchaseRequest;
import ProcureFlow.service.PurchaseRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-requests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    public PurchaseRequestController(
            PurchaseRequestService purchaseRequestService
    ) {
        this.purchaseRequestService = purchaseRequestService;
    }

    @GetMapping
    public List<PurchaseRequest> getAllPurchaseRequests() {
        return purchaseRequestService.getAllPurchaseRequests();
    }

    @PreAuthorize("hasAnyRole('REQUESTER', 'ADMIN')")
    @PostMapping
    public PurchaseRequest createPurchaseRequest(
            @Valid @RequestBody CreatePurchaseRequest request
    ) {
        return purchaseRequestService.createPurchaseRequest(request);
    }

    @PreAuthorize("hasAnyRole('APPROVER', 'ADMIN')")
    @PutMapping("/{id}/approval")
    public PurchaseRequest processApproval(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalRequest request
    ) {
        return purchaseRequestService.processApproval(id, request);
    }
}