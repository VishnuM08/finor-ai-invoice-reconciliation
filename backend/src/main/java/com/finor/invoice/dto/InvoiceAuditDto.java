package com.finor.invoice.dto;

import com.finor.invoice.entity.BankTransaction;
import com.finor.invoice.entity.Invoice;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceAuditDto {
    private Invoice invoice;
    private BankTransaction matchedTransaction;
}
