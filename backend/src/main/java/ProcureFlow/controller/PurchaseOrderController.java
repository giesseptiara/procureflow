package ProcureFlow.controller;

import ProcureFlow.entity.PurchaseOrder;
import ProcureFlow.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(
            PurchaseOrderService purchaseOrderService
    ) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @PostMapping
    public PurchaseOrder createPurchaseOrder(
            @RequestParam Long quotationId
    ) {
        return purchaseOrderService.createPurchaseOrder(quotationId);
    }

    @PutMapping("/{id}/issue")
    public PurchaseOrder issuePurchaseOrder(
            @PathVariable Long id
    ) {
        return purchaseOrderService.issuePurchaseOrder(id);
    }

    @PutMapping("/{id}/complete")
    public PurchaseOrder completePurchaseOrder(
            @PathVariable Long id
    ) {
        return purchaseOrderService.completePurchaseOrder(id);
    }
}