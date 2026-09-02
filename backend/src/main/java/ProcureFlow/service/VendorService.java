package ProcureFlow.service;

import ProcureFlow.dto.CreateVendorRequest;
import ProcureFlow.entity.Vendor;
import ProcureFlow.repository.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor createVendor(CreateVendorRequest request) {

        if (vendorRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vendor already exists"
            );
        }

        Vendor vendor = new Vendor(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress()
        );

        return vendorRepository.save(vendor);
    }

    public Vendor getVendorById(Long id) {

        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vendor not found"
                ));
    }

    public Vendor updateVendor(
            Long id,
            CreateVendorRequest request
    ) {

        Vendor vendor = getVendorById(id);

        if (vendorRepository.existsByNameIgnoreCase(request.getName())
                && !vendor.getName().equalsIgnoreCase(request.getName())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Vendor already exists"
            );
        }

        vendor.setName(request.getName());
        vendor.setEmail(request.getEmail());
        vendor.setPhone(request.getPhone());
        vendor.setAddress(request.getAddress());

        return vendorRepository.save(vendor);
    }

    public Vendor setVendorActive(
            Long id,
            boolean active
    ) {

        Vendor vendor = getVendorById(id);

        vendor.setActive(active);

        return vendorRepository.save(vendor);
    }
}