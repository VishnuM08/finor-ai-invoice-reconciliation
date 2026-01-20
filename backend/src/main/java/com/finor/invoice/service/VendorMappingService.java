package com.finor.invoice.service;

import com.finor.invoice.entity.GlAccount;
import com.finor.invoice.entity.VendorGlMapping;
import com.finor.invoice.repository.GlAccountRepository;
import com.finor.invoice.repository.VendorGlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorMappingService {

    private final VendorGlMappingRepository mappingRepo;
    private final GlAccountRepository glRepo;

    public VendorGlMapping createMapping(String keyword, String glCode) {

        GlAccount gl = glRepo.findByGlCode(glCode)
                .orElseThrow(() -> new RuntimeException("GL Code not found: " + glCode));

        VendorGlMapping mapping = VendorGlMapping.builder()
                .vendorKeyword(keyword.toLowerCase())
                .glAccount(gl)
                .active(true)
                .build();

        return mappingRepo.save(mapping);
    }

    public List<VendorGlMapping> getAll() {
        return mappingRepo.findAll();
    }

    public void delete(Long id) {
        mappingRepo.deleteById(id);
    }
}
