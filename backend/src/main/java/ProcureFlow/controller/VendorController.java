package ProcureFlow.controller;

import ProcureFlow.dto.CreateVendorRequest;
import ProcureFlow.entity.Vendor;
import ProcureFlow.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public Vendor getVendorById(
            @PathVariable Long id
    ) {
        return vendorService.getVendorById(id);
    }

    @PostMapping
    public Vendor createVendor(
            @Valid @RequestBody CreateVendorRequest request
    ) {
        return vendorService.createVendor(request);
    }

    @PutMapping("/{id}")
    public Vendor updateVendor(
            @PathVariable Long id,
            @Valid @RequestBody CreateVendorRequest request
    ) {
        return vendorService.updateVendor(id, request);
    }

    @PatchMapping("/{id}/status")
    public Vendor setVendorActive(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        return vendorService.setVendorActive(id, active);
    }
}