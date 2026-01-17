package com.finor.invoice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReconciliationSummaryDto {
    private long totalInvoices;
    private long uploaded;
    private long extracted;
    private long mapped;
    private long reconciled;
    private long notReconciled;
    private double successRate;
}
