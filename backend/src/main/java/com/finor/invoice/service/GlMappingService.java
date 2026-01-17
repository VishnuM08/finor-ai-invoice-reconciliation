package com.finor.invoice.service;

import org.springframework.stereotype.Service;

@Service
public class GlMappingService {

    public GlResult map(String vendorName, String extractedText) {

        String vendor = vendorName == null ? "" : vendorName.toLowerCase();
        String text = extractedText == null ? "" : extractedText.toLowerCase();

        // ✅ RULES (simple AI mapping rules)
        if (vendor.contains("uber") || text.contains("trip")) {
            return new GlResult("6100", "Travel Expense", "Travel Expense", 0.95);
        }

        if (vendor.contains("amazon") || vendor.contains("aws") || text.contains("cloud")) {
            return new GlResult("6400", "Cloud Services Expense", "Cloud / Hosting", 0.93);
        }

        if (vendor.contains("zoho") || text.contains("subscription")) {
            return new GlResult("6410", "SaaS Subscription", "Software Subscription", 0.92);
        }

        if (vendor.contains("office") || text.contains("a4") || text.contains("pen") || text.contains("stapler")) {
            return new GlResult("6200", "Office Supplies Expense", "Office Supplies", 0.90);
        }

        // Default
        return new GlResult("6999", "Other Expenses", "Uncategorized", 0.50);
    }

    // ✅ Simple response class
    public record GlResult(String glCode, String glName, String category, Double confidence) {}
}
