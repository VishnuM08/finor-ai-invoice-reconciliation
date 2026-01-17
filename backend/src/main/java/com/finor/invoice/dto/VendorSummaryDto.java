package com.finor.invoice.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorSummaryDto {
    private String vendor;
    private Double totalAmount;
    private Long count;
}
