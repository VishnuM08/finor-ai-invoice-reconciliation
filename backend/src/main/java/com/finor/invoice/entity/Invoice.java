package com.finor.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fileName;
	private String filePath;

	private String vendorName;
	private String invoiceNumber;
	private Double totalAmount;
	private String currency;

	private String category;
	private Double confidenceScore;

	@Column(length = 10000)
	private String extractedText;

	private String status; // UPLOADED, EXTRACTED, RECONCILED

	private LocalDateTime createdAt;

	private String glCode;
	private String glName;

	private Long matchedTxnId;

	@Column(length = 1000)
	private String matchedTxnDesc;

	private Double matchedTxnAmount;

	private LocalDate matchedTxnDate;

}
