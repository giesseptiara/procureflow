package ProcureFlow.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @Column(nullable = false)
    private Integer receivedQuantity;

    @Column(nullable = false)
    private LocalDate receivedDate;

    @Column(nullable = false, length = 50)
    private String condition;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Delivery() {
    }

    public Delivery(
            PurchaseOrder purchaseOrder,
            Integer receivedQuantity,
            LocalDate receivedDate,
            String condition,
            String notes
    ) {
        this.purchaseOrder = purchaseOrder;
        this.receivedQuantity = receivedQuantity;
        this.receivedDate = receivedDate;
        this.condition = condition;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public String getCondition() {
        return condition;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}