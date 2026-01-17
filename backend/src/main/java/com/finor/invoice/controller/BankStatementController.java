package com.finor.invoice.controller;

import com.finor.invoice.repository.BankTransactionRepository;
import com.finor.invoice.service.BankStatementService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.finor.invoice.entity.BankTransaction;


@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BankStatementController {

	private final BankStatementService bankStatementService;
	private final BankTransactionRepository bankRepo;


	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
		bankStatementService.uploadCsv(file);
		return ResponseEntity.ok("Bank statement uploaded successfully");
	}
	
	@GetMapping("/transactions")
	public ResponseEntity<List<BankTransaction>> list() {
	    return ResponseEntity.ok(bankRepo.findAll());
	}

}
