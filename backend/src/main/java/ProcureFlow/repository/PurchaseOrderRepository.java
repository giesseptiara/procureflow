package ProcureFlow.repository;

import ProcureFlow.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {

    boolean existsByQuotationId(Long quotationId);
}