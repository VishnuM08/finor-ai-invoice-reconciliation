package com.finor.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bank_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate txnDate;

	private String description;

	private Double amount;

	private String reference;

	private String matchedInvoiceNumber; // ✅ store invoice no if matched
	
	private Boolean reconciled; // true/false
}
