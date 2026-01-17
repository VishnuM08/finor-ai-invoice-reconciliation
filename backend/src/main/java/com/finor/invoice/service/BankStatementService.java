package com.finor.invoice.service;

import com.finor.invoice.entity.BankTransaction;
import com.finor.invoice.repository.BankTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BankStatementService {

	private final BankTransactionRepository bankRepo;

	public void uploadCsv(MultipartFile file) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;

		br.readLine(); // ✅ skip header

		while ((line = br.readLine()) != null) {
			String[] cols = line.split(",");

			//  CSV format: Date,Description,Amount,Reference
			//LocalDate date = LocalDate.parse(cols[0], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			LocalDate date = LocalDate.parse(cols[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			String description = cols[1];
			Double amount = Double.parseDouble(cols[2]);
			String reference = cols.length > 3 ? cols[3] : null;

			BankTransaction txn = BankTransaction.builder().txnDate(date).description(description).amount(amount)
					.reference(reference).reconciled(false).build();

			bankRepo.save(txn);
		}
	}
}
