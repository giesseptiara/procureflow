package ProcureFlow.controller;

import ProcureFlow.dto.CreateQuotationRequest;
import ProcureFlow.entity.Quotation;
import ProcureFlow.service.QuotationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotations")
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @GetMapping
    public List<Quotation> getAllQuotations() {
        return quotationService.getAllQuotations();
    }

    @GetMapping("/purchase-request/{purchaseRequestId}")
    public List<Quotation> getQuotationsByPurchaseRequest(
            @PathVariable Long purchaseRequestId
    ) {
        return quotationService.getQuotationsByPurchaseRequest(
                purchaseRequestId
        );
    }

    @PostMapping
    public Quotation createQuotation(
            @Valid @RequestBody CreateQuotationRequest request
    ) {
        return quotationService.createQuotation(request);
    }

    @PutMapping("/{id}/select")
    public Quotation selectQuotation(
            @PathVariable Long id
    ) {
        return quotationService.selectQuotation(id);
    }
}