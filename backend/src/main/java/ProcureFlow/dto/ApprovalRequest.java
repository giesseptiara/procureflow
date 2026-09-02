package ProcureFlow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ApprovalRequest {

    @NotNull(message = "Approved status is required")
    private Boolean approved;

    @Size(
            max = 500,
            message = "Rejection reason must not exceed 500 characters"
    )
    private String rejectionReason;

    public ApprovalRequest() {
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}