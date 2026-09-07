package ProcureFlow.controller;

import ProcureFlow.dto.CreateDeliveryRequest;
import ProcureFlow.entity.Delivery;
import ProcureFlow.service.DeliveryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/purchase-order/{purchaseOrderId}")
    public List<Delivery> getDeliveriesByPurchaseOrder(
            @PathVariable Long purchaseOrderId
    ) {
        return deliveryService.getDeliveriesByPurchaseOrder(purchaseOrderId);
    }

    @PostMapping
    public Delivery createDelivery(
            @Valid @RequestBody CreateDeliveryRequest request
    ) {
        return deliveryService.createDelivery(request);
    }
}