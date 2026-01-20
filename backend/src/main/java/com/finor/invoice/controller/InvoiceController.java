	package com.finor.invoice.controller;
	
	import com.finor.invoice.dto.InvoiceAuditDto;
import com.finor.invoice.entity.BankTransaction;
import com.finor.invoice.entity.Invoice;
	import com.finor.invoice.service.ReconciliationService;
	
	import com.finor.invoice.service.InvoiceService;
	import lombok.RequiredArgsConstructor;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import org.springframework.web.multipart.MultipartFile;
	
	import java.util.List;
	
	@RestController
	@RequestMapping("/api/invoices")
	@RequiredArgsConstructor
	public class InvoiceController {
	
	    private final InvoiceService invoiceService;
	    private final ReconciliationService reconciliationService;


	    
	    @PostMapping("/upload")
	    public ResponseEntity<Invoice> upload(@RequestParam("file") MultipartFile file) throws Exception {
	        return ResponseEntity.ok(invoiceService.upload(file));
	    }
	
	    @GetMapping
	    public ResponseEntity<List<Invoice>> list() {
	        return ResponseEntity.ok(invoiceService.getAll());
	    }
	
	    @PostMapping("/{id}/extract")
	    public ResponseEntity<Invoice> extract(@PathVariable Long id) throws Exception {
	        return ResponseEntity.ok(invoiceService.extract(id));
	    }
	
	    @PostMapping("/{id}/gl-map")
	    public ResponseEntity<Invoice> glMap(@PathVariable Long id) {
	        return ResponseEntity.ok(invoiceService.glMap(id));
	    }
	
	    @PostMapping("/{id}/reconcile")
	    public ResponseEntity<Invoice> reconcile(@PathVariable Long id) {
	        return ResponseEntity.ok(invoiceService.reconcile(id));
	    }
	    
	    @GetMapping("/{id}/audit")
	    public ResponseEntity<InvoiceAuditDto> audit(@PathVariable Long id) {

	        Invoice invoice = invoiceService.getById(id);
	       BankTransaction txn = reconciliationService.getMatchedTransaction(invoice.getInvoiceNumber());

	        return ResponseEntity.ok(
	                InvoiceAuditDto.builder()
	                        .invoice(invoice)
	                        .matchedTransaction(txn)
	                        .build()
	        );
	    }
	    
	    @PostMapping("/reconcile-all")
	    public ResponseEntity<String> reconcileAll() {
	        return ResponseEntity.ok(invoiceService.reconcileAll());
	    }




	}
