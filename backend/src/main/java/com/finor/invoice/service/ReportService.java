package com.finor.invoice.service;

import com.finor.invoice.dto.CategorySummaryDto;
import com.finor.invoice.dto.MonthlySummaryDto;
import com.finor.invoice.dto.ReconciliationSummaryDto;
import com.finor.invoice.dto.VendorSummaryDto;
import com.finor.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final InvoiceRepository invoiceRepository;

    public ReconciliationSummaryDto getSummary() {

        long total = invoiceRepository.count();
        long uploaded = invoiceRepository.countByStatus("UPLOADED");
        long extracted = invoiceRepository.countByStatus("EXTRACTED");
        long mapped = invoiceRepository.countByStatus("MAPPED");
        long reconciled = invoiceRepository.countByStatus("RECONCILED");
        long notReconciled = invoiceRepository.countByStatus("NOT_RECONCILED");

        double successRate = total == 0 ? 0 : ((double) reconciled / total) * 100;

        return ReconciliationSummaryDto.builder()
                .totalInvoices(total)
                .uploaded(uploaded)
                .extracted(extracted)
                .mapped(mapped)
                .reconciled(reconciled)
                .notReconciled(notReconciled)
                .successRate(successRate)
                .build();
    }
   

    public List<CategorySummaryDto> getCategorySummary(){
        return invoiceRepository.categorySummary()
            .stream()
            .map(row -> new CategorySummaryDto(
                    (String) row[0],
                    (Double) row[1],
                    (Long) row[2]
            ))
            .collect(Collectors.toList());
    }

    public List<VendorSummaryDto> getVendorSummary(){
        return invoiceRepository.vendorSummary()
            .stream()
            .map(row -> new VendorSummaryDto(
                    (String) row[0],
                    (Double) row[1],
                    (Long) row[2]
            ))
            .collect(Collectors.toList());
    }

    public List<MonthlySummaryDto> getMonthlySummary(){
        return invoiceRepository.monthlySummary()
            .stream()
            .map(row -> new MonthlySummaryDto(
                    (String) row[0],
                    (Double) row[1],
                    (Long) row[2]
            ))
            .collect(Collectors.toList());
    }

}
