package com.finor.invoice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.finor.invoice.entity.Invoice;
import com.finor.invoice.repository.InvoiceRepository;

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
        return invoiceRepository.findAll();
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

        var result = glMappingService.map(invoice.getVendorName(), invoice.getExtractedText());

        invoice.setGlCode(result.glCode());
        invoice.setGlName(result.glName());
        invoice.setCategory(result.category());
        invoice.setConfidenceScore(result.confidence());
        invoice.setStatus("MAPPED");

        return invoiceRepository.save(invoice);
    }

    public Invoice reconcile(Long invoiceId) {
        return reconciliationService.reconcile(invoiceId);
    }
    
    public Invoice getById(Long id){
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

}
