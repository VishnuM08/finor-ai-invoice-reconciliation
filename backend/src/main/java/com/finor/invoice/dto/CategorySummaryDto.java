package com.finor.invoice.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorySummaryDto {
    private String category;
    private Double totalAmount;
    private Long count;
}
