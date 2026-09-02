package ProcureFlow.repository;

import ProcureFlow.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    boolean existsByNameIgnoreCase(String name);
}