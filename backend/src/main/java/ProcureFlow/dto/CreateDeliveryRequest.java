package ProcureFlow.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateDeliveryRequest {

    @NotNull(message = "Purchase order ID is required")
    private Long purchaseOrderId;

    @NotNull(message = "Received quantity is required")
    @Min(value = 1, message = "Received quantity must be at least 1")
    private Integer receivedQuantity;

    @NotNull(message = "Received date is required")
    private LocalDate receivedDate;

    @NotBlank(message = "Condition is required")
    @Size(max = 50, message = "Condition must not exceed 50 characters")
    private String condition;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;

    public CreateDeliveryRequest() {
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}