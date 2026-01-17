package com.finor.invoice.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlySummaryDto {
    private String month;
    private Double totalAmount;
    private Long count;
}
