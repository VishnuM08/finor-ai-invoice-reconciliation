package com.finor.invoice.controller;

import com.finor.invoice.entity.VendorGlMapping;
import com.finor.invoice.service.VendorMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-mapping")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class VendorGlMappingController {

    private final VendorMappingService mappingService;

    @PostMapping
    public ResponseEntity<VendorGlMapping> create(
            @RequestParam String keyword,
            @RequestParam String glCode
    ) {
        return ResponseEntity.ok(mappingService.createMapping(keyword, glCode));
    }

    @GetMapping
    public ResponseEntity<List<VendorGlMapping>> list() {
        return ResponseEntity.ok(mappingService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mappingService.delete(id);
        return ResponseEntity.ok().build();
    }
}
