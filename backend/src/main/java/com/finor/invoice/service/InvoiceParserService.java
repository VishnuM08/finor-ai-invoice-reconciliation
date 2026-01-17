package com.finor.invoice.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InvoiceParserService {

	public String extractVendor(String text) {
		return find(text, "Vendor:\\s*(.*)");
	}

	public String extractInvoiceNumber(String text) {
		return find(text, "Invoice No:\\s*(.*)");
	}

	public Double extractTotalAmount(String text) {
		String total = find(text, "(?i)\\bTotal:\\s*([0-9,]+\\.?[0-9]*)");

		if (total == null)
			return null;
		return Double.parseDouble(total.replace(",", ""));
	}

	private String find(String text, String regex) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find())
			return matcher.group(1).trim();
		return null;
	}
}
