package ProcureFlow.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateQuotationRequest {

    @NotNull(message = "Purchase request ID is required")
    private Long purchaseRequestId;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    @NotNull(message = "Offered price is required")
    @DecimalMin(
            value = "0.01",
            message = "Offered price must be greater than 0"
    )
    private BigDecimal offeredPrice;

    @Size(
            max = 500,
            message = "Notes must not exceed 500 characters"
    )
    private String notes;

    public CreateQuotationRequest() {
    }

    public Long getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public void setPurchaseRequestId(Long purchaseRequestId) {
        this.purchaseRequestId = purchaseRequestId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public BigDecimal getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(BigDecimal offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}