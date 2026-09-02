package ProcureFlow.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotations")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_request_id", nullable = false)
    private PurchaseRequest purchaseRequest;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal offeredPrice;

    @Column(length = 500)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuotationStatus status;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    public Quotation() {
    }

    public Quotation(
            PurchaseRequest purchaseRequest,
            Vendor vendor,
            BigDecimal offeredPrice,
            String notes
    ) {
        this.purchaseRequest = purchaseRequest;
        this.vendor = vendor;
        this.offeredPrice = offeredPrice;
        this.notes = notes;
        this.status = QuotationStatus.SUBMITTED;
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public BigDecimal getOfferedPrice() {
        return offeredPrice;
    }

    public String getNotes() {
        return notes;
    }

    public QuotationStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setOfferedPrice(BigDecimal offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStatus(QuotationStatus status) {
        this.status = status;
    }
}