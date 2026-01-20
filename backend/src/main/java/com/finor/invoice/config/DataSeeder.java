package com.finor.invoice.config;

import com.finor.invoice.entity.GlAccount;
import com.finor.invoice.entity.VendorGlMapping;
import com.finor.invoice.repository.GlAccountRepository;
import com.finor.invoice.repository.VendorGlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final GlAccountRepository glRepo;
    private final VendorGlMappingRepository mappingRepo;

    @Override
    public void run(String... args) {

        // 1) Create GL accounts if missing
        GlAccount travel = glRepo.findByGlCode("6100").orElseGet(() ->
                glRepo.save(GlAccount.builder()
                        .glCode("6100").glName("Travel Expense").category("Travel").build())
        );

        GlAccount saas = glRepo.findByGlCode("6400").orElseGet(() ->
                glRepo.save(GlAccount.builder()
                        .glCode("6400").glName("Software & SaaS").category("Software").build())
        );

        GlAccount gstInput = glRepo.findByGlCode("14160").orElseGet(() ->
                glRepo.save(GlAccount.builder()
                        .glCode("14160").glName("Input GST Receivable").category("Tax").build())
        );

        // 2) Insert mappings if table is empty
        if (mappingRepo.count() == 0) {
            mappingRepo.save(VendorGlMapping.builder().vendorKeyword("uber").glAccount(travel).build());
            mappingRepo.save(VendorGlMapping.builder().vendorKeyword("ola").glAccount(travel).build());
            mappingRepo.save(VendorGlMapping.builder().vendorKeyword("zoho").glAccount(saas).build());
            mappingRepo.save(VendorGlMapping.builder().vendorKeyword("amazon").glAccount(saas).build());
            mappingRepo.save(VendorGlMapping.builder().vendorKeyword("gst").glAccount(gstInput).build());
        }
    }
}
