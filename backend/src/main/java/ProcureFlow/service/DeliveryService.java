package ProcureFlow.service;

import ProcureFlow.dto.CreateDeliveryRequest;
import ProcureFlow.entity.Delivery;
import ProcureFlow.entity.PurchaseOrder;
import ProcureFlow.entity.PurchaseOrderStatus;
import ProcureFlow.repository.DeliveryRepository;
import ProcureFlow.repository.PurchaseOrderRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public DeliveryService(
            DeliveryRepository deliveryRepository,
            PurchaseOrderRepository purchaseOrderRepository
    ) {
        this.deliveryRepository = deliveryRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> getDeliveriesByPurchaseOrder(Long purchaseOrderId) {

        if (!purchaseOrderRepository.existsById(purchaseOrderId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Purchase order not found"
            );
        }

        return deliveryRepository.findByPurchaseOrderId(purchaseOrderId);
    }

    @Transactional
public Delivery createDelivery(CreateDeliveryRequest request)  {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(
                request.getPurchaseOrderId()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Purchase order not found"
        ));

        if (purchaseOrder.getStatus() != PurchaseOrderStatus.ISSUED
                && purchaseOrder.getStatus() != PurchaseOrderStatus.PARTIALLY_RECEIVED) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Delivery can only be recorded for issued purchase orders"
            );
        }

        int totalReceived = deliveryRepository
                .findByPurchaseOrderId(purchaseOrder.getId())
                .stream()
                .mapToInt(Delivery::getReceivedQuantity)
                .sum();

        int newTotalReceived =
                totalReceived + request.getReceivedQuantity();

        int orderedQuantity = purchaseOrder
                .getQuotation()
                .getPurchaseRequest()
                .getQuantity();

        if (newTotalReceived > orderedQuantity) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Received quantity cannot exceed ordered quantity"
            );
        }

        Delivery delivery = new Delivery(
                purchaseOrder,
                request.getReceivedQuantity(),
                request.getReceivedDate(),
                request.getCondition(),
                request.getNotes()
        );

        Delivery savedDelivery = deliveryRepository.save(delivery);

        if (newTotalReceived == orderedQuantity) {
            purchaseOrder.setStatus(PurchaseOrderStatus.RECEIVED);
        } else {
            purchaseOrder.setStatus(PurchaseOrderStatus.PARTIALLY_RECEIVED);
        }

        purchaseOrderRepository.save(purchaseOrder);

        return savedDelivery;
    }
}