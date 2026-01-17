package com.finor.invoice.service;

import com.finor.invoice.entity.BankTransaction;
import com.finor.invoice.entity.Invoice;
import com.finor.invoice.repository.BankTransactionRepository;
import com.finor.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReconciliationService {

	private final InvoiceRepository invoiceRepo;
	private final BankTransactionRepository bankRepo;


	public Invoice reconcile(Long invoiceId) {

		Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found"));

		List<BankTransaction> txns = bankRepo.findAll();

		for (BankTransaction txn : txns) {

			if (Boolean.TRUE.equals(txn.getReconciled()))
				continue;

			// ✅ amount tolerance
			boolean amountMatch = invoice.getTotalAmount() != null && txn.getAmount() != null
					&& Math.abs(txn.getAmount() - invoice.getTotalAmount()) < 1.0;

			// ✅ vendor keyword match
			String vendorToken = (invoice.getVendorName() == null) ? ""
					: invoice.getVendorName().split(" ")[0].toLowerCase();

			boolean vendorMatch = txn.getDescription() != null
					&& txn.getDescription().toLowerCase().contains(vendorToken);

			if (amountMatch && vendorMatch) {
				txn.setMatchedInvoiceNumber(invoice.getInvoiceNumber());
				txn.setReconciled(true);
				bankRepo.save(txn);

				invoice.setStatus("RECONCILED");
				return invoiceRepo.save(invoice);
			}
		}

		invoice.setStatus("NOT_RECONCILED");
		return invoiceRepo.save(invoice);
	}

	public BankTransaction getMatchedTransaction(String invoiceNumber) {
		return bankRepo.findAll().stream().filter(txn -> Boolean.TRUE.equals(txn.getReconciled()))
				.filter(txn -> invoiceNumber != null && invoiceNumber.equals(txn.getMatchedInvoiceNumber())).findFirst()
				.orElse(null);
	}

}
