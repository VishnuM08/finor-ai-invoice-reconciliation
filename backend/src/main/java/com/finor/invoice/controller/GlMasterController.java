package com.finor.invoice.controller;

import com.finor.invoice.entity.GlAccount;
import com.finor.invoice.service.GlMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gl")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class GlMasterController {

    private final GlMasterService glService;

    @PostMapping
    public ResponseEntity<GlAccount> create(@RequestBody GlAccount gl) {
        return ResponseEntity.ok(glService.create(gl));
    }

    @GetMapping
    public ResponseEntity<List<GlAccount>> list() {
        return ResponseEntity.ok(glService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        glService.delete(id);
        return ResponseEntity.ok().build();
    }
}
