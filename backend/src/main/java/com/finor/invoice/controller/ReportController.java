package com.finor.invoice.controller;

import com.finor.invoice.dto.*;
import com.finor.invoice.dto.ReconciliationSummaryDto;
import com.finor.invoice.service.ReportService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reconciliation-summary")
    public ResponseEntity<ReconciliationSummaryDto> summary() {
        return ResponseEntity.ok(reportService.getSummary());
    }
    
    @GetMapping("/category-summary")
    public ResponseEntity<List<CategorySummaryDto>> categorySummary(){
        return ResponseEntity.ok(reportService.getCategorySummary());
    }

    @GetMapping("/vendor-summary")
    public ResponseEntity<List<VendorSummaryDto>> vendorSummary(){
        return ResponseEntity.ok(reportService.getVendorSummary());
    }

    @GetMapping("/monthly-summary")
    public ResponseEntity<List<MonthlySummaryDto>> monthlySummary(){
        return ResponseEntity.ok(reportService.getMonthlySummary());
    }

}
