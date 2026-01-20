package com.finor.invoice.repository;

import com.finor.invoice.entity.VendorGlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VendorGlMappingRepository extends JpaRepository<VendorGlMapping, Long> {
    Optional<VendorGlMapping> findByVendorKeywordIgnoreCase(String vendorKeyword);

	List<VendorGlMapping> findByActiveTrue();
    
    //@Query("SELECT v FROM VendorGlMapping v WHERE v.active = true")
   // List<VendorGlMapping> findActiveMappings();
}
