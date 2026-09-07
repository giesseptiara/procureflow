package ProcureFlow.repository;

import ProcureFlow.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByPurchaseOrderId(Long purchaseOrderId);
}