package com.finor.invoice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finor.invoice.entity.VendorGlMapping;
import com.finor.invoice.repository.VendorGlMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GlMappingService {

    private final VendorGlMappingRepository vendorMappingRepo;

    public VendorGlMapping findMapping(String vendorName, String extractedText) {

        String vendor = normalize(vendorName == null ? "" : vendorName);
        String text = normalize(extractedText == null ? "" : extractedText);

        List<VendorGlMapping> mappings = vendorMappingRepo.findByActiveTrue();

        for (VendorGlMapping m : mappings) {
            String keyword = normalize(m.getVendorKeyword());

            if (!keyword.isBlank() && (vendor.contains(keyword) || text.contains(keyword))) {
                return m;
            }
        }

        return null; // not found
    }

    private String normalize(String input) {
        return input.toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
