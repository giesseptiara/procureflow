package ProcureFlow.repository;

import ProcureFlow.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationRepository
        extends JpaRepository<Quotation, Long> {

    List<Quotation> findByPurchaseRequestId(
            Long purchaseRequestId
    );

    boolean existsByPurchaseRequestIdAndVendorId(
            Long purchaseRequestId,
            Long vendorId
    );
}