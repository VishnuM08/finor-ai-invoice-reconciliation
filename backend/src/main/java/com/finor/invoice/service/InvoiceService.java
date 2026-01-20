package com.finor.invoice.service;

import java.time.LocalDateTime;
import java.util.*;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.finor.invoice.entity.Invoice;
import com.finor.invoice.entity.VendorGlMapping;
import com.finor.invoice.repository.InvoiceRepository;
import com.finor.invoice.repository.VendorGlMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final FileStorageService fileStorageService;
    private final OcrService ocrService;
    private final InvoiceParserService parserService;
    private final GlMappingService glMappingService;
    private final ReconciliationService reconciliationService;
    private final VendorGlMappingRepository vendorMappingRepo;

    public Invoice upload(MultipartFile file) throws Exception {
        String path = fileStorageService.save(file);

        Invoice invoice = Invoice.builder()
                .fileName(file.getOriginalFilename())
                .filePath(path)
                .status("UPLOADED")
                .createdAt(LocalDateTime.now())
                .build();

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAll() {
    	return invoiceRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

    }

    public Invoice extract(Long id) throws Exception {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        String text = ocrService.extractTextFromFile(invoice.getFilePath());

        invoice.setExtractedText(text);
        invoice.setVendorName(parserService.extractVendor(text));
        invoice.setInvoiceNumber(parserService.extractInvoiceNumber(text));
        invoice.setTotalAmount(parserService.extractTotalAmount(text));
        invoice.setStatus("EXTRACTED");

        return invoiceRepository.save(invoice);
    }

    public Invoice glMap(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        VendorGlMapping mapping = glMappingService.findMapping(
                invoice.getVendorName(),
                invoice.getExtractedText()
        );

        if (mapping != null) {
            invoice.setGlCode(mapping.getGlAccount().getGlCode());
            invoice.setGlName(mapping.getGlAccount().getGlName());
            invoice.setCategory(mapping.getGlAccount().getCategory());
            invoice.setConfidenceScore(1.0);
            invoice.setStatus("MAPPED");
        } else {
            invoice.setStatus("MAPPING_NOT_FOUND");
            invoice.setConfidenceScore(0.0);
        }

        return invoiceRepository.save(invoice);
    }


    public Invoice reconcile(Long invoiceId) {
        return reconciliationService.reconcile(invoiceId);
    }
    
    public Invoice getById(Long id){
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public String reconcileAll() {
        List<Invoice> mappedInvoices = invoiceRepository.findByStatus("MAPPED");

        int total = mappedInvoices.size();
        int success = 0;
        int failed = 0;

        for (Invoice inv : mappedInvoices) {
            Invoice result = reconciliationService.reconcile(inv.getId());
            if ("RECONCILED".equals(result.getStatus())) success++;
            else failed++;
        }

        return "Reconcile completed ✅ Total: " + total +
                " | Success: " + success +
                " | Failed: " + failed;
    }

  

}
