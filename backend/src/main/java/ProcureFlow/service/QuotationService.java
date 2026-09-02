package ProcureFlow.service;

import ProcureFlow.dto.CreateQuotationRequest;
import ProcureFlow.entity.PurchaseRequest;
import ProcureFlow.entity.PurchaseRequestStatus;
import ProcureFlow.entity.Quotation;
import ProcureFlow.entity.Vendor;
import ProcureFlow.repository.PurchaseRequestRepository;
import ProcureFlow.repository.QuotationRepository;
import ProcureFlow.repository.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final VendorRepository vendorRepository;

    public QuotationService(
            QuotationRepository quotationRepository,
            PurchaseRequestRepository purchaseRequestRepository,
            VendorRepository vendorRepository
    ) {
        this.quotationRepository = quotationRepository;
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.vendorRepository = vendorRepository;
    }

    public List<Quotation> getAllQuotations() {
        return quotationRepository.findAll();
    }

    public List<Quotation> getQuotationsByPurchaseRequest(
            Long purchaseRequestId
    ) {
        return quotationRepository.findByPurchaseRequestId(
                purchaseRequestId
        );
    }

    public Quotation createQuotation(
            CreateQuotationRequest request
    ) {

        // 1. Cari Purchase Request
        PurchaseRequest purchaseRequest =
                purchaseRequestRepository.findById(
                        request.getPurchaseRequestId()
                ).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Purchase request not found"
                ));

        // 2. Purchase Request harus sudah APPROVED
        if (purchaseRequest.getStatus()
                != PurchaseRequestStatus.APPROVED) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Quotation can only be created for an approved purchase request"
            );
        }

        // 3. Cari Vendor
        Vendor vendor = vendorRepository.findById(
                request.getVendorId()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Vendor not found"
        ));

        // 4. Vendor harus aktif
        if (!vendor.getActive()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vendor is inactive"
            );
        }

        // 5. Vendor tidak boleh mengirim quotation dua kali
        if (quotationRepository
                .existsByPurchaseRequestIdAndVendorId(
                        request.getPurchaseRequestId(),
                        request.getVendorId()
                )) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vendor has already submitted a quotation for this purchase request"
            );
        }

        // 6. Buat quotation
        Quotation quotation = new Quotation(
                purchaseRequest,
                vendor,
                request.getOfferedPrice(),
                request.getNotes()
        );

        return quotationRepository.save(quotation);
    }

    public Quotation selectQuotation(Long id) {

    // 1. Cari quotation
    Quotation selectedQuotation =
            quotationRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Quotation not found"
                    ));

    // 2. Pastikan quotation masih SUBMITTED
    if (selectedQuotation.getStatus()
            != ProcureFlow.entity.QuotationStatus.SUBMITTED) {

        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Quotation has already been processed"
        );
    }

    // 3. Cari semua quotation untuk Purchase Request yang sama
    List<Quotation> quotations =
            quotationRepository.findByPurchaseRequestId(
                    selectedQuotation.getPurchaseRequest().getId()
            );

    // 4. Tandai quotation yang dipilih
    selectedQuotation.setStatus(
            ProcureFlow.entity.QuotationStatus.SELECTED
    );

    // 5. Tolak quotation lainnya
    for (Quotation quotation : quotations) {

        if (!quotation.getId()
                .equals(selectedQuotation.getId())) {

            quotation.setStatus(
                    ProcureFlow.entity.QuotationStatus.REJECTED
            );
        }
    }

    // 6. Simpan perubahan
    quotationRepository.saveAll(quotations);

    return selectedQuotation;
}
}