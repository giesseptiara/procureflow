package ProcureFlow.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "quotation_id", nullable = false, unique = true)
    private Quotation quotation;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseOrderStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public PurchaseOrder() {
    }

    public PurchaseOrder(
            Quotation quotation,
            Vendor vendor,
            BigDecimal totalAmount
    ) {
        this.quotation = quotation;
        this.vendor = vendor;
        this.totalAmount = totalAmount;
        this.status = PurchaseOrderStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }
}