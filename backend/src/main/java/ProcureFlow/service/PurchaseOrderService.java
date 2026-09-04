package ProcureFlow.service;

import ProcureFlow.entity.PurchaseOrder;
import ProcureFlow.entity.PurchaseOrderStatus;
import ProcureFlow.entity.Quotation;
import ProcureFlow.entity.QuotationStatus;
import ProcureFlow.repository.PurchaseOrderRepository;
import ProcureFlow.repository.QuotationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final QuotationRepository quotationRepository;

    public PurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository,
            QuotationRepository quotationRepository
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.quotationRepository = quotationRepository;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder createPurchaseOrder(Long quotationId) {

        // 1. Cari quotation
        Quotation quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Quotation not found"
                ));

        // 2. Hanya quotation SELECTED yang boleh dibuatkan PO
        if (quotation.getStatus() != QuotationStatus.SELECTED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Purchase order can only be created from a selected quotation"
            );
        }

        // 3. Pastikan quotation belum memiliki PO
        if (purchaseOrderRepository.existsByQuotationId(quotationId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Purchase order already exists for this quotation"
            );
        }

        // 4. Buat PO
        PurchaseOrder purchaseOrder = new PurchaseOrder(
                quotation,
                quotation.getVendor(),
                quotation.getOfferedPrice()
        );

        // 5. PO pertama kali dibuat sebagai DRAFT
        purchaseOrder.setStatus(PurchaseOrderStatus.DRAFT);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder issuePurchaseOrder(Long id) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Purchase order not found"
                ));

        if (purchaseOrder.getStatus() != PurchaseOrderStatus.DRAFT) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Only draft purchase orders can be issued"
            );
        }

        purchaseOrder.setStatus(PurchaseOrderStatus.ISSUED);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder completePurchaseOrder(Long id) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Purchase order not found"
                ));

        if (purchaseOrder.getStatus() != PurchaseOrderStatus.ISSUED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Only issued purchase orders can be completed"
            );
        }

        purchaseOrder.setStatus(PurchaseOrderStatus.COMPLETED);

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder cancelPurchaseOrder(Long id) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Purchase order not found"
                ));

        if (purchaseOrder.getStatus() == PurchaseOrderStatus.COMPLETED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Completed purchase orders cannot be cancelled"
            );
        }

        if (purchaseOrder.getStatus() == PurchaseOrderStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Purchase order is already cancelled"
            );
        }

        purchaseOrder.setStatus(PurchaseOrderStatus.CANCELLED);

        return purchaseOrderRepository.save(purchaseOrder);
    }
}